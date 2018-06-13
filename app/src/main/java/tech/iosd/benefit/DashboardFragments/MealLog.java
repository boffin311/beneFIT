package tech.iosd.benefit.DashboardFragments;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.Model.DatabaseHandler;
import tech.iosd.benefit.Model.MealLogFood;
import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.ResponseForFoodSearch;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;

public class MealLog extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener
{
    public Calendar selDate;

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    ArrayList<String> ingredientTyp = new ArrayList<>();
    ArrayList<String> ingredientsQty;
    Context ctx;
    View rootView;
    FragmentManager fm;
    ListView breakfastListView;
    ArrayList<String> breakfastIngredients;
    ListView midMorningListView;
    ArrayList<String> midMorningIngredients;
    ListView lunchListView;
    ArrayList<String> lunchIngredients;
    ListView snackListView;
    ArrayList<String> snacksIngredients;
    ListView dinnerListView;
    ArrayList<String> dinnerIngredients;
    ArrayList<MealLogFood > listItems;
    tech.iosd.benefit.Adapters.MealLog adapter ;

    private CompositeSubscription mSubscriptions;

    private DatabaseHandler db ;
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_meal_log, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        mSubscriptions = new CompositeSubscription();

        db = new DatabaseHandler(getContext());

        listItems = new ArrayList<>();
        MealLogFood mealLogFood =  new MealLogFood();
        mealLogFood.setName("ya");
        listItems.add(mealLogFood);



        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.meal_log_calendar)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .mode(HorizontalCalendar.Mode.DAYS)
                .configure()
                .formatMiddleText("EEE\n").sizeMiddleText(12)
                .formatBottomText("dd").sizeBottomText(26)
                .showTopText(false)
                .end()
                .build();

        final TextView lbl_year = rootView.findViewById(R.id.meal_log_calendar_year);
        final TextView lbl_month = rootView.findViewById(R.id.meal_log_calendar_month);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                selDate = date;
                lbl_year.setText(String.valueOf(selDate.get(Calendar.YEAR)));
                lbl_month.setText(months[selDate.get(Calendar.MONTH)]);
            }
        });

        ingredientsQty = new ArrayList<>();
        for (int i = 1; i < 100; i++)
            ingredientsQty.add(Integer.toString(i));
        ingredientTyp.add("Add a food");

        breakfastListView = rootView.findViewById(R.id.my_nutrition_breakfast);
        breakfastIngredients = new ArrayList<>();
        breakfastIngredients.add("2 Egg Whites");
        breakfastIngredients.add("4 Rotis");
        breakfastIngredients.add("1 Glass of mix fruit juice");
        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
        breakfastListView.setAdapter(breakfastAdapter);
        breakfastListView.setOnItemClickListener(this);
        breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();
        rootView.findViewById(R.id.my_nutrition_breakfast_add).setOnClickListener(this);

        midMorningListView = rootView.findViewById(R.id.my_nutrition_mid_morning);
        midMorningIngredients = new ArrayList<>();
        midMorningIngredients.add("2 Egg Whites");
        midMorningIngredients.add("4 Rotis");
        midMorningIngredients.add("1 Glass of mix fruit juice");
        final ArrayAdapter<String> midMorningAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, midMorningIngredients);
        midMorningListView.setAdapter(midMorningAdapter);
        midMorningListView.setOnItemClickListener(this);
        midMorningListView.getLayoutParams().height = 110 * midMorningIngredients.size();
        rootView.findViewById(R.id.my_nutrition_mid_morning_add).setOnClickListener(this);

        lunchListView = rootView.findViewById(R.id.my_nutrition_lunch);
        lunchIngredients = new ArrayList<>();
        lunchIngredients.add("2 Egg Whites");
        lunchIngredients.add("4 Rotis");
        lunchIngredients.add("1 Glass of mix fruit juice");
        final ArrayAdapter<String> lunchAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, lunchIngredients);
        lunchListView.setAdapter(lunchAdapter);
        lunchListView.setOnItemClickListener(this);
        lunchListView.getLayoutParams().height = 110 * lunchIngredients.size();
        rootView.findViewById(R.id.my_nutrition_lunch_add).setOnClickListener(this);

        snackListView = rootView.findViewById(R.id.my_nutrition_snacks);
        snacksIngredients = new ArrayList<>();
        snacksIngredients.add("2 Egg Whites");
        snacksIngredients.add("4 Rotis");
        snacksIngredients.add("1 Glass of mix fruit juice");
        final ArrayAdapter<String> snacksAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, snacksIngredients);
        snackListView.setAdapter(snacksAdapter);
        snackListView.setOnItemClickListener(this);
        snackListView.getLayoutParams().height = 110 * snacksIngredients.size();
        rootView.findViewById(R.id.my_nutrition_snack_add).setOnClickListener(this);

        dinnerListView = rootView.findViewById(R.id.my_nutrition_dinner);
        dinnerIngredients = new ArrayList<>();
        dinnerIngredients.add("2 Egg Whites");
        dinnerIngredients.add("4 Rotis");
        dinnerIngredients.add("1 Glass of mix fruit juice");
        final ArrayAdapter<String> dinnerAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, dinnerIngredients);
        dinnerListView.setAdapter(dinnerAdapter);
        dinnerListView.setOnItemClickListener(this);
        dinnerListView.getLayoutParams().height = 110 * dinnerIngredients.size();
        rootView.findViewById(R.id.my_nutrition_dinner_add).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_ingredient, null);
        TextView dialogTitle = mView.findViewById(R.id.dialog_picker_ingredient_title);
        Button dialogModify = mView.findViewById(R.id.dialog_modify);
        Button dialogRemove = mView.findViewById(R.id.dialog_remove);
        final WheelPicker wheelPickerQty = mView.findViewById(R.id.dialog_picker_ingredient_qty);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        wheelPickerQty.setData(ingredientsQty);
        //wheelPickerTyp.setData(ingredientTyp);

        final int pos = i;

        switch (adapterView.getId())
        {
            case R.id.my_nutrition_breakfast:
            {
                dialogTitle.setText(breakfastIngredients.get(pos));
                dialogModify.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //breakfastIngredients.set(pos, wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " + wheelPickerTyp.getData().get(wheelPickerTyp.getCurrentItemPosition()));
                        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
                        breakfastListView.setAdapter(breakfastAdapter);
                        dialog.dismiss();
                    }
                });
                dialogRemove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        breakfastIngredients.remove(pos);
                        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
                        breakfastListView.setAdapter(breakfastAdapter);
                        breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();
                        dialog.dismiss();
                    }
                });
                break;
            }
            case R.id.my_nutrition_mid_morning:
            {
                dialogTitle.setText(midMorningIngredients.get(pos));
                dialogModify.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                       // midMorningIngredients.set(pos, wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " + wheelPickerTyp.getData().get(wheelPickerTyp.getCurrentItemPosition()));
                        final ArrayAdapter<String> midMorningAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, midMorningIngredients);
                        midMorningListView.setAdapter(midMorningAdapter);
                        dialog.dismiss();
                    }
                });
                dialogRemove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        midMorningIngredients.remove(pos);
                        final ArrayAdapter<String> midMorningAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, midMorningIngredients);
                        midMorningListView.setAdapter(midMorningAdapter);
                        midMorningListView.getLayoutParams().height = 110 * midMorningIngredients.size();
                        dialog.dismiss();
                    }
                });
                break;
            }
            case R.id.my_nutrition_lunch:
            {
                dialogTitle.setText(lunchIngredients.get(pos));
                dialogModify.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //lunchIngredients.set(pos, wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " +
                        // .getData().get(wheelPickerTyp.getCurrentItemPosition()));
                        final ArrayAdapter<String> lunchAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, lunchIngredients);
                        lunchListView.setAdapter(lunchAdapter);
                        dialog.dismiss();
                    }
                });
                dialogRemove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        lunchIngredients.remove(pos);
                        final ArrayAdapter<String> lunchAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, lunchIngredients);
                        lunchListView.setAdapter(lunchAdapter);
                        lunchListView.getLayoutParams().height = 110 * lunchIngredients.size();
                        dialog.dismiss();
                    }
                });
                break;
            }
            case R.id.my_nutrition_snacks:
            {
                dialogTitle.setText(snacksIngredients.get(pos));
                dialogModify.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //snacksIngredients.set(pos, wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " + wheelPickerTyp.getData().get(wheelPickerTyp.getCurrentItemPosition()));
                        final ArrayAdapter<String> snacksAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, snacksIngredients);
                        snackListView.setAdapter(snacksAdapter);
                        dialog.dismiss();
                    }
                });
                dialogRemove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        snacksIngredients.remove(pos);
                        final ArrayAdapter<String> snacksAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, snacksIngredients);
                        snackListView.setAdapter(snacksAdapter);
                        snackListView.getLayoutParams().height = 110 * snacksIngredients.size();
                        dialog.dismiss();
                    }
                });
                break;
            }
            case R.id.my_nutrition_dinner:
            {
                dialogTitle.setText(dinnerIngredients.get(pos));
                dialogModify.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //dinnerIngredients.set(pos, wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " + wheelPickerTyp.getData().get(wheelPickerTyp.getCurrentItemPosition()));
                        final ArrayAdapter<String> dinnerAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, dinnerIngredients);
                        dinnerListView.setAdapter(dinnerAdapter);
                        dialog.dismiss();
                    }
                });
                dialogRemove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dinnerIngredients.remove(pos);
                        final ArrayAdapter<String> dinnerAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, dinnerIngredients);
                        dinnerListView.setAdapter(dinnerAdapter);
                        dinnerListView.getLayoutParams().height = 110 * dinnerIngredients.size();
                        dialog.dismiss();
                    }
                });
                break;
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.my_nutrition_breakfast_add:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_ingredient_add, null);
                Button dialogAdd = mView.findViewById(R.id.dialog_add);
                Button dialogCancel = mView.findViewById(R.id.dialog_cancel);
                final WheelPicker wheelPickerQty = mView.findViewById(R.id.dialog_picker_ingredient_qty);
                //final WheelPicker wheelPickerTyp = mView.findViewById(R.id.dialog_picker_ingredient_typ);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                recyclerView = dialog.findViewById(R.id.dialog_picker_ingredient_recycler_view);
                recyclerView.setHasFixedSize(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter = new tech.iosd.benefit.Adapters.MealLog(dialog.getContext(), listItems, getActivity());
                recyclerView.setAdapter(adapter);

                EditText foodName = (EditText)mView.findViewById(R.id.dialog_picker_ingredient_food_name);
                (foodName).setOnEditorActionListener(
                        new EditText.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                        actionId == EditorInfo.IME_ACTION_DONE ||
                                        event != null &&
                                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                    if (event == null || !event.isShiftPressed()) {

                                        String name =  foodName.getText().toString();
                                        Toast.makeText(getContext(),name,Toast.LENGTH_LONG).show();

                                        getSearchResult(name);
                                        return true;
                                    }
                                }
                                return false;
                            }
                        }
                );

                //wheelPickerQty.setData(ingredientsQty);
                //wheelPickerTyp.setData(ingredientTyp);

                dialogAdd.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                       // breakfastIngredients.add(wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " + wheelPickerTyp.getData().get(wheelPickerTyp.getCurrentItemPosition()));
                        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
                        breakfastListView.setAdapter(breakfastAdapter);
                        breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();
                        dialog.dismiss();
                    }
                });
                dialogCancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });
                break;
            }
        }
    }

    private void getSearchResult(String name) {

        mSubscriptions.add(NetworkUtil.getRetrofit(db.getUserToken()).getFoodList(name,db.getUserToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));

    }
    private void handleResponse(ResponseForFoodSearch response) {

        //Toast.makeText(getActivity().getApplicationContext(),token,Toast.LENGTH_SHORT).show();
        listItems.clear();

        listItems = response.getData();
        listItems.add(response.getData().get(0));

        Toast.makeText(getActivity().getApplicationContext(),String.valueOf(listItems.size()),Toast.LENGTH_SHORT).show();


        adapter.notifyDataSetChanged();
        adapter.setListItems(listItems);
        Toast.makeText(getActivity().getApplicationContext(),"m"+String.valueOf(adapter.getItemCount()),Toast.LENGTH_SHORT).show();

        recyclerView.setAdapter(adapter);

        Toast.makeText(getActivity().getApplicationContext(),"mq"+String.valueOf(adapter.getItemCount()),Toast.LENGTH_SHORT).show();




    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("error77",error.getMessage());

            showSnackBarMessage("Network Error !");
        }
    }
    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_LONG).show();

        }
    }
}
