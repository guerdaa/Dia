package com.android.dia.ui.measures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dia.R
import com.android.dia.utils.adapter.MeasureAdapter
import com.android.dia.viewmodel_factories.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.measures_fragment.*
import javax.inject.Inject

class MeasuresFragment : DaggerFragment() {

    companion object {
        fun newInstance() = MeasuresFragment()
    }

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: MeasuresViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.measures_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, providerFactory).get(MeasuresViewModel::class.java)
        viewModel.retrieveMeasure()
        viewModel.deleted.observe(viewLifecycleOwner, Observer { deleted ->
            if(deleted) {
                Toast.makeText(context, "Measure deleted", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.retrieved.observe(viewLifecycleOwner, Observer {retrieved ->
            if(retrieved) {
                measures_recycler_view.adapter = object : MeasureAdapter(viewModel.measures) {
                    override fun deleteMeasure() {
                        viewModel.deleteMeasure(viewModel.measures[clickedItemPosition].id)
                        viewModel.measures.removeAt(clickedItemPosition)
                        measures_recycler_view.adapter?.notifyDataSetChanged()
                    }
                }
                measures_recycler_view.layoutManager = LinearLayoutManager(context)
                refresh_layout.isRefreshing = false
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh_layout.setOnRefreshListener {
            viewModel.retrieveMeasure()
        }
    }
}
