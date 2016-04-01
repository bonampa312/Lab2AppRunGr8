package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lab2apprun.gr8.compumovil.udea.edu.co.lab2apprun.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListElementFragment extends Fragment {


    public ListElementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_element, container, false);
    }

}
