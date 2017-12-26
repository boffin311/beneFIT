package tech.iosd.benefit.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.iosd.benefit.R;

/**
 * Created by Anubhav on 27-12-2017.
 */

public class EnterOtpFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.enter_otp_fragment,container,false);





        return v;
    }
}
