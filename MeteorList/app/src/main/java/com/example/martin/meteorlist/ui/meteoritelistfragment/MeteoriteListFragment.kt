package com.example.martin.meteorlist.ui.meteoritelistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.martin.meteorlist.R
import com.example.martin.meteorlist.databinding.FragmentMeteoriteListBinding
import com.example.martin.meteorlist.di.Injectable
import com.example.martin.meteorlist.model.Meteorite
import com.example.martin.meteorlist.ui.MeteoriteInterface
import com.example.martin.meteorlist.utils.METEORITE_KEY
import com.example.martin.meteorlist.utils.autoCleared
import javax.inject.Inject


class MeteoriteListFragment : Fragment(), MeteoriteInterface, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mBinding by autoCleared<FragmentMeteoriteListBinding>()

    private var mAdapter by autoCleared<MeteoriteListAdapter>()

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
        meteoriteListViewModel.connectToApi()
        meteoriteListViewModel.scheduleApiWorker()
        mAdapter = MeteoriteListAdapter(this)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
        meteoriteFromDatabase()
        meteoriteApiError()
    }

    private fun meteoriteFromDatabase() {
        meteoriteListViewModel.meteoritesFromDatabase().observe(this, Observer { it ->
            if (!it.isEmpty()) {
                mAdapter.setMeteoriteList(it)
                mBinding.numberMeteoritesFall = it.lastIndex + 1
            }
        })
    }

    private fun meteoriteApiError(){
        meteoriteListViewModel.meteoriteError().observe(this, Observer<String>{
            Toast.makeText(
                context, resources.getString(R.string.movie_error_message) + it,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onClick(meteorite: Meteorite) {
        val args = Bundle()
        args.putParcelable(METEORITE_KEY, meteorite)
        findNavController().navigate(R.id.mapsDetailFragment, args)
    }
}
