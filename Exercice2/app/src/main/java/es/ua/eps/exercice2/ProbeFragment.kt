package es.ua.eps.exercice2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import es.eps.ua.sharedprefeferences.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [ProbeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProbeFragment : PreferenceFragmentCompat() {
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME_BUNDLE)
            address = it.getString(ADDRESS_BUNDLE)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        
        return inflater.inflate(R.layout.fragment_probe, container, false)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    companion object {

        const val NAME_BUNDLE = "name_bundle"
        const val ADDRESS_BUNDLE = "address_bundle"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProbeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProbeFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(NAME_BUNDLE, name)
                    putString(ADDRESS_BUNDLE, address)
                }
            }
    }
}