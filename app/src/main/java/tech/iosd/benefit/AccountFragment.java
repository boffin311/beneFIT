package tech.iosd.benefit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment implements
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener{
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 11;
    Context ctx;
    FragmentManager fm;

    TextView googleFit;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        googleFit = rootView.findViewById(R.id.googleFit);
        googleFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FitnessOptions fitnessOptions = FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .build();

                if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getActivity()), fitnessOptions)) {
                    GoogleSignIn.requestPermissions(
                            getActivity(), // your activity
                            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                            GoogleSignIn.getLastSignedInAccount(getActivity()),
                            fitnessOptions);
                } else {
                    accessGoogleFit();
                }
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    private void accessGoogleFit() {
        GoogleSignInOptionsExtension fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_ACTIVITY_SAMPLES, FitnessOptions.ACCESS_READ)
                        .build();

        GoogleSignInAccount gsa = GoogleSignIn.getAccountForExtension(getActivity(), fitnessOptions);

        Task<Void> response = Fitness.getRecordingClient(getActivity(), gsa)
                .subscribe(DataType.TYPE_ACTIVITY_SAMPLES);
        // play services starts recording stuff now
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(getContext(),"GoogleFit Connected",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(),"GoogleFit Connection Suspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"GoogleFit Connection Failed",Toast.LENGTH_SHORT).show();
    }

    //runs only if authorization is required
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                accessGoogleFit();
            }
        }
    }
}
