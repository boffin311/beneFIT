package tech.iosd.benefit.Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tech.iosd.benefit.R;

/**
 * Created by Anubhav on 27-12-2017.
 */

public class GetOtpFragment extends Fragment {
    Button submitPhoneButton;
    EditText enterPhoneEditText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.get_otp_fragment,container,false);

        enterPhoneEditText=v.findViewById(R.id.enter_otp_phone_number);

        submitPhoneButton= v.findViewById(R.id.get_otp_proceed);

        submitPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterPhoneEditText.getText().toString().length()!=10){
                    enterPhoneEditText.setError("Please enter your 10 digit phone number");
                }
                else{
                    //TODO: start firebase auth from here...
                }
            }
        });
        return v;
    }
}
