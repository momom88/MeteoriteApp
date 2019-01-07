package com.example.martin.meteorlist.ui.meteoritelistfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.martin.meteorlist.R
import com.example.martin.meteorlist.databinding.FragmentMeteoriteListBinding
import com.example.martin.meteorlist.di.Injectable
import com.example.martin.meteorlist.model.Meteorite
import com.example.martin.meteorlist.ui.MeteoriteInterface
import com.example.martin.meteorlist.utils.METEORITE_KEY
import com.example.martin.meteorlist.utils.autoCleared
import com.example.martin.meteorlist.worker.ApiWorker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class MeteoriteListFragment : Fragment(), MeteoriteInterface, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding by autoCleared<FragmentMeteoriteListBinding>()

    private var mAdapter by autoCleared<MeteoriteListAdapter>()

    private val compositeDisposable by lazy { CompositeDisposable() }

    private lateinit var meteoriteListViewModel: MeteoriteListViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_meteorite_list, container, false
        )
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        meteoriteListViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(MeteoriteListViewModel::class.java)
        meteoriteListViewModel.scheduleApiWorker()
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        meteoriteFromDatabase()
        setupViewModel()
    }

    private fun meteoriteFromDatabase() {
        meteoriteListViewModel.meteoritesFromDatabase().observe(this, Observer { it ->
            if (!it.isEmpty()) {
                mBinding.loadingIndicator.visibility = GONE
                mBinding.recyclerView.visibility = VISIBLE
                mAdapter = MeteoriteListAdapter(this, it)
                mBinding.cvNumberCount.visibility = VISIBLE
                mBinding.tvNumberCount.text = resources.getString(R.string.number, it.lastIndex + 1)
                mBinding.recyclerView.adapter = mAdapter
            }
        })
    }

    private fun setupViewModel() {
        compositeDisposable.add(
            meteoriteListViewModel.getMeteorites()
                .subscribeBy(
                    onSuccess = {
                        Log.i("ApiWorker", "setupViewModel On Success")
                    },
                    onError = {
                        mBinding.loadingIndicator.visibility = GONE
                        Toast.makeText(
                            context, resources.getString(R.string.movie_error_message) + it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun onClick(meteorite: Meteorite) {
        val args = Bundle()
        args.putParcelable(METEORITE_KEY, meteorite)
        findNavController().navigate(R.id.mapsDetailFragment, args)
    }
}
