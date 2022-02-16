package com.example.recipe_book_mobile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_ORDERED_LIST = "orderedList"
private const val ARG_DATA = "data"
private const val ARG_TITLE = "title"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DropListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DropListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DropListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var data: ArrayList<String> = arrayListOf()
    private var title: String = "Loading"
    private var orderedList: Boolean? = null
    private var listener: OnFragmentInteractionListener? = null


    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getStringArrayList(ARG_DATA) ?: data
            title = it.getString(ARG_TITLE) ?: title
            orderedList = it.getBoolean(ARG_ORDERED_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_drop_list, container, false)
        rootView.findViewById<RecyclerView>(R.id.recycler_view_drop_list).apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = DropListAdapter(data, orderedList ?: false)
        }

        rootView.findViewById<TextView>(R.id.text_view_drop_list_title).apply{
            this.text = title
        }

        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title  String.
         * @param data Parameter 2.
         * @return A new instance of fragment DropListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String, data: ArrayList<String>, orderedList: Boolean = false) =
            DropListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putStringArrayList(ARG_DATA, data)
                    putBoolean(ARG_ORDERED_LIST, orderedList)
                }
            }
    }
}
