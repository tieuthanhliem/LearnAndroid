package com.tieuthanhliem.android.criminalintent

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes  ${crimes.size}")
                    updateUI(crimes)
                }

            }
        )
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime
        private var titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private var dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private var solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text  = DateFormat.format("EEEE, MMM d, yyyy", this.crime.date)
            solvedImageView.visibility = if(crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeAdapter(val crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = when (viewType) {
                VIEW_TYPE_NORMAL_CRIME -> layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                VIEW_TYPE_SERIOUS_CRIME -> layoutInflater.inflate(R.layout.list_item_serious_crime, parent, false)
                else -> throw Exception("view type not support")

            }
            return CrimeHolder(view)
        }

        override fun getItemCount(): Int = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            holder.bind(crimes[position])
        }

        override fun getItemViewType(position: Int): Int {
            return VIEW_TYPE_NORMAL_CRIME
            return when {
                crimes[position].requiresPolice -> VIEW_TYPE_SERIOUS_CRIME
                else -> VIEW_TYPE_NORMAL_CRIME
            }
        }
    }

    companion object {
        const val VIEW_TYPE_NORMAL_CRIME = 1
        const val VIEW_TYPE_SERIOUS_CRIME = 2
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}
