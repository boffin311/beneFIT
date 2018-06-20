package tech.iosd.benefit.DashboardFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.Model.BodyForMealLog;
import tech.iosd.benefit.Model.DatabaseHandler;
import tech.iosd.benefit.Model.MealLogForOneMeal;
import tech.iosd.benefit.Model.MealLogFood;
import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.ResponseForFoodSearch;
import tech.iosd.benefit.Model.ResponseForGetMeal;
import tech.iosd.benefit.Model.ResponseForSuccess;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;
import tech.iosd.benefit.Model.ResponseForGetMeal.Food;
import tech.iosd.benefit.Utils.Constants;

import static android.app.Activity.RESULT_OK;

public class MealLog extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener
{
    //private class MealUpdateThenUpload;

    public Calendar selDate;
    SimpleDateFormat dateFormat;

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

    TextView dialogCarbs, dialogProtien, dialogCalorie, dialogFats;
    TextView breakfastCarbs, breakfastProtien,breakfastCalorie, breakfastFats;
    TextView midmorningCarbs, midmorningProtien,midmorningCalorie, midmorningFats;


    private ProgressDialog progressDialog;



    private CompositeSubscription mSubscriptions;
    private CompositeSubscription mSubscriptionsSearch;



    private DatabaseHandler db ;
    RecyclerView recyclerView;
    int position = -1;
    MealLogForOneMeal mealLogBreakfast;
    MealLogForOneMeal mealLogMidmorning;
    private String selectedDate;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dashboard_meal_log, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        mSubscriptions = new CompositeSubscription();
        mSubscriptionsSearch = new CompositeSubscription();
        mealLogBreakfast = new MealLogForOneMeal(getContext());
        mealLogMidmorning = new MealLogForOneMeal(getContext());

        db = new DatabaseHandler(getContext());
        progressDialog =  new ProgressDialog(ctx);
        //progressDialog.show();
        progressDialog.setMessage("Please Wait..");

        progressDialog.setCancelable(false);

        listItems = new ArrayList<>();
        MealLogFood mealLogFood =  new MealLogFood();
        mealLogFood.setName("Please search a Food item");
        listItems.add(mealLogFood);

        breakfastCalorie = rootView.findViewById(R.id.meal_log_breakfast_calorie);
        breakfastProtien = rootView.findViewById(R.id.meal_log_breakfast_protien);
        breakfastCarbs = rootView.findViewById(R.id.meal_log_breakfast_carb);
        breakfastFats = rootView.findViewById(R.id.meal_log_breakfast_fat);

        midmorningCalorie = rootView.findViewById(R.id.meal_log_midmorning_calorie);
        midmorningProtien = rootView.findViewById(R.id.meal_log_midmorning_protien);
        midmorningCarbs = rootView.findViewById(R.id.meal_log_midmorning_carb);
        midmorningFats = rootView.findViewById(R.id.meal_log_midmorning_fat);



        ingredientsQty = new ArrayList<>();
        for (int i = 1; i < 100; i++)
            ingredientsQty.add(Integer.toString(i));
        ingredientTyp.add("gram");
        ingredientTyp.add("piece");
        ingredientTyp.add("bowl");
        ingredientTyp.add("katori");
        ingredientTyp.add("serve");


        breakfastListView = rootView.findViewById(R.id.my_nutrition_breakfast);
        breakfastIngredients = new ArrayList<>();
        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
        breakfastListView.setAdapter(breakfastAdapter);
        breakfastListView.setOnItemClickListener(this);
        breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();
        rootView.findViewById(R.id.my_nutrition_breakfast_add).setOnClickListener(this);


        midMorningListView = rootView.findViewById(R.id.my_nutrition_mid_morning);
        midMorningIngredients = new ArrayList<>();
        /*
        midMorningIngredients.add("2 Egg Whites");
        midMorningIngredients.add("4 Rotis");
        midMorningIngredients.add("1 Glass of mix fruit juice");*/
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

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        selectedDate = dateFormat.format(Calendar.getInstance().getTime());

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.meal_log_calendar)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .mode(HorizontalCalendar.Mode.DAYS)
                .configure()
                .formatMiddleText("EEEEE\n").sizeMiddleText(12)
                .formatBottomText("dd").sizeBottomText(18)
                .showTopText(false)
                .end()
                .build();

        final TextView lbl_year = rootView.findViewById(R.id.meal_log_calendar_year);
        final TextView lbl_month = rootView.findViewById(R.id.meal_log_calendar_month);
        lbl_year.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        lbl_month.setText(months[Calendar.getInstance().get(Calendar.MONTH)]);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                //progressDialog.show();
                selDate = date;
                selectedDate = dateFormat.format(date.getTime());
                Toast.makeText(getContext(),selectedDate,Toast.LENGTH_SHORT).show();
                lbl_year.setText(String.valueOf(selDate.get(Calendar.YEAR)));
                lbl_month.setText(months[selDate.get(Calendar.MONTH)]);
                breakfastIngredients.clear();
                mealLogBreakfast.onDateChange();
                final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
                breakfastListView.setAdapter(breakfastAdapter);
                breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();
                getMealData("breakfast");
                updateUI("breakfast");
            }
        });


        getMealData("breakfast");
        updateUI("breakfast");


        return rootView;
    }

    private void getMealData(String meal) {
       // progressDialog.show();

            mSubscriptions.add(NetworkUtil.getRetrofit(db.getUserToken()).getFoodMeal(selectedDate,meal,db.getUserToken())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseGetMeal,this::handleErrorGetMeal));



    }

    private void handleResponseGetMeal(ResponseForGetMeal response) {

        //Toast.makeText(getContext(),"",Toast.LENGTH_LONG).show();
        //Log.d("error77",response.getMessage());

        progressDialog.hide();



        if(response.isSuccess()){
            for (int i = 0; i< response.getData().getFood().size(); i++){
                breakfastIngredients.add(response.getData().getFood().get(i).getQuantity() + " " +response.getData().getFood().get(i).getUnit()+" "+ response.getData().getFood().get(i).getItem().getName());
                Toast.makeText(getContext(),"default :"+response.getData().getFood().get(i).getItem().getDefaultSize(),Toast.LENGTH_LONG).show();
                mealLogBreakfast.addMeal(new Food(response.getData().getFood().get(i).getQuantity(),response.getData().getFood().get(i).getItem()),response.getData().getFood().get(i).getQuantity(),response.getData().getFood().get(i).getUnit());
               /* mealLogBreakfast.setMealCalorie(mealLogBreakfast.getMealCalorie()+response.getData().getFood().get(i).getItem().getCalories() * response.getData().getFood().get(i).getQuantity());
                mealLogBreakfast.setMealCarbs(mealLogBreakfast.getMealCarbs()+response.getData().getFood().get(i).getItem().getCarbs()* response.getData().getFood().get(i).getQuantity());
                mealLogBreakfast.setMealFat(mealLogBreakfast.getMealFat()+response.getData().getFood().get(i).getItem().getFats()* response.getData().getFood().get(i).getQuantity());
                mealLogBreakfast.setMealProtien(mealLogBreakfast.getMealProtien()+response.getData().getFood().get(i).getItem().getProteins()* response.getData().getFood().get(i).getQuantity());
*/

                updateUI("breakfast");

            }
        }





        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
        breakfastListView.setAdapter(breakfastAdapter);
        breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();



    }

    private void handleErrorGetMeal(Throwable error) {

        Log.d("error77",error.getMessage());


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());
                fm.popBackStack();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("error77",error.getMessage());

            showSnackBarMessage("Network Error !");
        }
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
        final WheelPicker wheelPickerTyp = mView.findViewById(R.id.dialog_picker_ingredient_type);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        wheelPickerQty.setData(ingredientsQty);
        wheelPickerTyp.setData(ingredientTyp);
        wheelPickerTyp.setSelectedItemPosition(1);

        final int pos = i;

        Toast.makeText(getContext(),String.valueOf(pos),Toast.LENGTH_SHORT).show();

        switch (adapterView.getId())
        {
            case R.id.my_nutrition_breakfast:
            {
                dialogTitle.setText(breakfastIngredients.get(pos));
                String unit = mealLogBreakfast.getMeal().get(pos).getUnit();
                int quantity = mealLogBreakfast.getMeal().get(pos).getQuantity();
                if(unit.equalsIgnoreCase("gram")){

                    wheelPickerTyp.setSelectedItemPosition(0);

                }else if(unit.equalsIgnoreCase("piece")){

                    wheelPickerTyp.setSelectedItemPosition(1);

                }else if(unit.equalsIgnoreCase("bowl")){

                    wheelPickerTyp.setSelectedItemPosition(2);

                }else if(unit.equalsIgnoreCase("katori")){

                    wheelPickerTyp.setSelectedItemPosition(3);

                }else if(unit.equalsIgnoreCase("serve")){

                    wheelPickerTyp.setSelectedItemPosition(4);

                }else {
                    Toast.makeText(getContext(),"new unit found"+" "+ unit,Toast.LENGTH_LONG).show();
                    fm.popBackStack();
                }
                wheelPickerQty.setSelectedItemPosition(quantity-1);

                dialogModify.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //breakfastIngredients.set(pos, wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " + wheelPickerTyp.getData().get(wheelPickerTyp.getCurrentItemPosition()));


                        int quantity = wheelPickerQty.getCurrentItemPosition()+ 1;
                        String unit = ingredientTyp.get(wheelPickerTyp.getCurrentItemPosition());

                        breakfastIngredients.set(pos,""+quantity + " " +unit+" "+ listItems.get(position).getName());
                        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);



                        mealLogBreakfast.updateMealAt(mealLogBreakfast.getMeal().get(pos),quantity,unit,pos);





                        uploadMealLogToServer("breakfast");
                        updateUI("breakfast");


                        breakfastListView.setAdapter(breakfastAdapter);
                        dialog.dismiss();
                    }
                });
                dialogRemove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        /*breakfastCalorie.setText(String.valueOf(Float.parseFloat(breakfastCalorie.getText().toString())- (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getCalories()));
                        breakfastProtien.setText(String.valueOf(Float.parseFloat(breakfastProtien.getText().toString())- (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getProteins()));
                        breakfastFats.setText(String.valueOf(Float.parseFloat(breakfastFats.getText().toString()) - (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getFats()));
                        breakfastCarbs.setText(String.valueOf(Float.parseFloat(breakfastCarbs.getText().toString()) - (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getCarbs()));*/
                        breakfastIngredients.remove(pos);

                        /*mealLogBreakfast.setMealCalorie(mealLogBreakfast.getMealCalorie()- (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getCalories());
                        mealLogBreakfast.setMealCarbs(mealLogBreakfast.getMealCarbs()- (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getCarbs());
                        mealLogBreakfast.setMealProtien(mealLogBreakfast.getMealProtien()- (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getProteins());
                        mealLogBreakfast.setMealFat(mealLogBreakfast.getMealFat()- (mealLogBreakfast.getMeal().get(pos).getQuantity()) * mealLogBreakfast.getMeal().get(pos).getItem().getFats());
*/
                        mealLogBreakfast.removeMealAt(pos);



                        final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
                        breakfastListView.setAdapter(breakfastAdapter);
                        breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();
                        uploadMealLogToServer("breakfast");
                        updateUI("breakfast");

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
                MealLogSearch mealLogSearch =  new MealLogSearch();
                mealLogSearch.setTargetFragment(this,Constants.ADD_DATA_REQUEST_OK);
                Bundle bundle = new Bundle();
                bundle.putString("mealType","breakfast");
                mealLogSearch.setArguments(bundle);
                fm.beginTransaction().addToBackStack(MealLog.class.getName()).replace(R.id.dashboard_content, mealLogSearch,"tag").commit();


/*
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_DeviceDefault_Dialog);
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_ingredient_add, null);
                Button dialogAdd = mView.findViewById(R.id.dialog_add);
                Button dialogCancel = mView.findViewById(R.id.dialog_cancel);
                final WheelPicker wheelPickerQty = mView.findViewById(R.id._qty);

                //final WheelPicker wheelPickerTyp = mView.findViewById(R.id.dialog_picker_ingredient_typ);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialogCarbs = (TextView)dialog.findViewById(R.id.dialog_picker_ingredient_add_carbs);
                dialogFats = (TextView)dialog.findViewById(R.id.dialog_picker_ingredient_add_fats);
                dialogCalorie = (TextView)dialog.findViewById(R.id.dialog_picker_ingredient_add_calories);
                dialogProtien = (TextView)dialog.findViewById(R.id.dialog_picker_ingredient_add_protien);

                recyclerView = dialog.findViewById(R.id.dialog_picker_ingredient_add_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new tech.iosd.benefit.Adapters.MealLog(dialog.getContext(), listItems, getActivity(), this);
                recyclerView.setAdapter(adapter);





                wheelPickerQty.setData(ingredientsQty);
                //wheelPickerTyp.setData(ingredientTyp);

                dialogAdd.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if(position ==-1){
                            Toast.makeText(getContext(),"Please select an input",Toast.LENGTH_LONG).show();
                        }
                        else {
                            breakfastIngredients.add(wheelPickerQty.getData().get(wheelPickerQty.getCurrentItemPosition()) + " " +listItems.get(position).getUnit()+" "+ listItems.get(position).getName());


                                mealLogBreakfast.addMeal(new ResponseForGetMeal.Food(wheelPickerQty.getCurrentItemPosition() + 1,listItems.get(position)),wheelPickerQty.getCurrentItemPosition()+1,listItems.get(position).getUnit());
                                Toast.makeText(getActivity().getApplicationContext(),"dea" +listItems.get(position).getDefaultSize()+ " "+ listItems.get(position).getSize().serve,Toast.LENGTH_SHORT).show();


                            final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
                            breakfastListView.setAdapter(breakfastAdapter);
                            breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();




                            dialog.dismiss();
                        }

                    }
                });
                dialogCancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });*/
                break;
            }
        }
    }
    private void updateUI(String meal)  {
       /* ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("working..");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        try{
            wait(1000);

        }catch (Exception e){

        }
        if(meal.equalsIgnoreCase("breakfast")){
            breakfastCalorie.setText(String.valueOf(mealLogBreakfast.getMealCalorie()));
            breakfastProtien.setText(String.valueOf(mealLogBreakfast.getMealProtien()));
            breakfastFats.setText(String.valueOf(mealLogBreakfast.getMealFat()));
            breakfastCarbs.setText(String.valueOf(mealLogBreakfast.getMealCarbs()));
        }
        progressDialog.hide();
    }

    private void uploadMealLogToServer(String meal){
        ArrayList<BodyForMealLog.Food> food1 =  new ArrayList<>();
       // progressDialog.show();

        if(meal.equalsIgnoreCase("breakfast")){
            for(int i = 0; i< mealLogBreakfast.getMeal().size(); i++){
                String id = mealLogBreakfast.getMeal().get(i).getItem().getId();
                int quantity = mealLogBreakfast.getMeal().get(i).getQuantity();
                food1.add(new BodyForMealLog.Food(id,quantity,mealLogBreakfast.getMeal().get(i).getUnit()));
                Toast.makeText(getContext()," "+mealLogBreakfast.getMeal().get(i).getItem().getSize().serve+" "+mealLogBreakfast.getMeal().get(i).getItem().getSize().gram,Toast.LENGTH_SHORT).show();
                Log.d("error77",String.valueOf(i));
//                Log.d("error77",id);


            }

            Log.d("error77","breakfast found");
        }

        BodyForMealLog bodyForMealLog = new BodyForMealLog(selectedDate,meal, food1);
        mSubscriptions.add(NetworkUtil.getRetrofit(db.getUserToken()).sendFoodMeal(bodyForMealLog,db.getUserToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseSendMealLog,this::handleError));
    }

    private void handleResponseSendMealLog(ResponseForSuccess responseForSuccess){
        showSnackBarMessage("Data saved to server.");

        progressDialog.hide();
        updateUI("breakfast");
    }


    private void handleResponse(ResponseForFoodSearch response) {

        //Toast.makeText(getActivity().getApplicationContext(),token,Toast.LENGTH_SHORT).show();
        listItems.clear();
        for(int i =0; i<response.getData().size(); i++){
            listItems.add(response.getData().get(i));
        }

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //Toast.makeText(getActivity().getApplicationContext(),"mq"+String.valueOf(adapter.getItemCount()),Toast.LENGTH_SHORT).show();




    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();
            showSnackBarMessage("Network Error !");
            Log.d("error77",error.getMessage());

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                /*Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());*/

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
            //progressDialog.hide();

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode== Constants.ADD_DATA_REQUEST_OK){
                //progressDialog =  new ProgressDialog(getContext());
                //progressDialog.setMessage("Please Wait..");

                //progressDialog.setCancelable(true);


                String gson = data.getExtras().getString("meal");
                MealLogFood newFood = (new Gson()).fromJson(gson,MealLogFood.class);
                String mealType = data.getExtras().getString("mealType");

                if(mealType.equalsIgnoreCase("breakfast")){
                    breakfastIngredients.add("1" + " " +newFood.getUnit()+" "+ newFood.getName());
                    mealLogBreakfast.addMeal(new ResponseForGetMeal.Food( 1,newFood),+1,newFood.getUnit());
                    final ArrayAdapter<String> breakfastAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, breakfastIngredients);
                    breakfastListView.setAdapter(breakfastAdapter);
                    breakfastListView.getLayoutParams().height = 110 * breakfastIngredients.size();
                    updateUI("breakfast");
                    uploadMealLogToServer("breakfast");
                }





            }
        }
    }


}
