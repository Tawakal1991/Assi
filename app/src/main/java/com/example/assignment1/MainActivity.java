package com.example.assignment1;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Integer currentPage=0;
    private Integer previousPage=0;

    //Friends
    private DatabaseManager mydManager;
    private TextView response;
    private TextView productRec;
    private EditText myid, fn, ln, gender, age, address,img;
    private Spinner spinner1;
    private Button addButton, homeButton, prevButton, saveButton, cancelButton, deleteButton;
    private TableLayout addLayout;
    private boolean recInserted;
    private ListView list;
    private Button button12;
    private CustomAdapter adapter1;
    private boolean[] checkboxes;
    private ImageView userImg;
    private ImageView ch1,ch2,ch3,ch4;


    //Tasks
    private EditText taskID, taskName, locationName;
    private Spinner statusSpinner;
    private Button createTask, deleteTask, saveTask;
    private TableLayout addTaskLayout;
    private DatabaseManagerTasks taskManager;
    private ListView listTasks,listTasks2;
    private TextView TextNotComplete, TextComplete;

    //Events
    private DatabaseManagerEvents eventManager;
    private EditText eventID, eventName, eventLocation;
    private TextView flag, datedate, timetime;
    private DatePicker eventDate;
    private TimePicker eventTime;
    private Button createEvent;
    private TableLayout addEventLayout;
    private ListView listEvents;
    private CustomAdapter adapter69;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Events
        eventID = (EditText)findViewById(R.id.eventID);
        eventName = (EditText)findViewById(R.id.eventName);
        eventDate = (DatePicker)findViewById(R.id.eventDate);
        eventTime = (TimePicker)findViewById(R.id.eventTime);
        eventTime.setIs24HourView(true);
        eventLocation = (EditText)findViewById(R.id.eventLocation);
        flag = (TextView)findViewById(R.id.flag);
        datedate = (TextView)findViewById(R.id.datedate);
        timetime = (TextView)findViewById(R.id.timetime);
        createEvent = (Button)findViewById(R.id.createEvent);
        addEventLayout = (TableLayout)findViewById(R.id.eventTable);
        eventManager = new DatabaseManagerEvents(MainActivity.this);
        listEvents = (ListView)findViewById(R.id.listTasks);




        //Tasks
        taskID = (EditText)findViewById(R.id.taskID);
        taskName = (EditText)findViewById(R.id.taskName);
        locationName = (EditText)findViewById(R.id.location);
        statusSpinner = (Spinner)findViewById(R.id.status);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.task_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter2);
        createTask = (Button)findViewById(R.id.createTask);
        addTaskLayout = (TableLayout)findViewById(R.id.taskTable);
        taskManager = new DatabaseManagerTasks(MainActivity.this);
        listTasks = (ListView)findViewById(R.id.listTasks);
        listTasks2 = (ListView)findViewById(R.id.listTasks2);
        deleteTask = (Button)findViewById(R.id.deleteTask);
        saveTask = (Button)findViewById(R.id.saveTask);
        TextNotComplete = (TextView)findViewById(R.id.TextNotComplete);
        TextComplete = (TextView)findViewById(R.id.TextComplete);


        //Friends
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Gender_Array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        myid = (EditText)findViewById(R.id.idmee);
        homeButton = (Button)findViewById(R.id.homee);//Home Button
        prevButton = (Button)findViewById(R.id.prevB);//Prev Button
        img=(EditText)findViewById(R.id.imggg);
        userImg=(ImageView)findViewById(R.id.usrImg);

        button12 = (Button)findViewById(R.id.deleteButton);
        ch1=(ImageView)findViewById(R.id.imgChoose1);
        ch2=(ImageView)findViewById(R.id.imgChoose2);
        ch3=(ImageView)findViewById(R.id.imgChoose3);
        ch4=(ImageView)findViewById(R.id.imgChoose4);

        list = (ListView)findViewById(R.id.list1);

        deleteButton = (Button)findViewById(R.id.Del_Button);

        mydManager = new DatabaseManager(MainActivity.this);
        response = (TextView)findViewById(R.id.response);
        productRec = (TextView)findViewById(R.id.prodrec);
        addLayout = (TableLayout)findViewById(R.id.add_table);

        response.setText("Welcome to Assignment 1 \n This is the Home Page \n Press MENU button to display menu");
        saveButton = (Button)findViewById(R.id.Save_button);

        addButton = (Button) findViewById(R.id.add_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        hideAll();
        //ADD NEW FRIEND BUTTON
        addButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //id = (EditText)findViewById(R.id.id);
                fn = (EditText)findViewById(R.id.fn);
                ln = (EditText)findViewById(R.id.ln);

                age = (EditText)findViewById(R.id.age);
                address = (EditText)findViewById(R.id.address);
                recInserted = mydManager.addRow( fn.getText().toString(), ln.getText().toString(), String.valueOf(spinner1.getSelectedItem()), Integer.parseInt(age.getText().toString()), address.getText().toString(), img.getText().toString());
                addLayout.setVisibility(View.GONE);
                if (recInserted) {
                    response.setText("The row in the Friends table is inserted");
                }
                else {
                    response.setText("Sorry, errors when inserting to DB");
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mydManager.close();
                //id.setText("");
                fn.setText("");
                ln.setText("");
                spinner1.setSelection(0);
                age.setText("");
                address.setText("");
                productRec.setText("");
            }
        });

        //EDIT FRIEND BUTTON
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)//when someone clicks on a friend record in Edit Friend
            {
                String item = (String) list.getAdapter().getItem(position);
                String[] separated = item.split(",");
                //Toast.makeText(getApplicationContext(), "ID: " + separated[0] + " selected", Toast.LENGTH_LONG).show();
                int MyID = Integer. parseInt(separated[0]);
                hideAll();
                //img.setVisibility(View.VISIBLE);
                userImg.setVisibility(View.VISIBLE);
                homeButton.setVisibility(View.VISIBLE);
                prevButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                addLayout.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                response.setText("Edit Friend Information");
                ch1.setVisibility(View.VISIBLE);
                ch2.setVisibility(View.VISIBLE);
                ch3.setVisibility(View.VISIBLE);
                ch4.setVisibility(View.VISIBLE);
                myid = (EditText)findViewById(R.id.idmee);
                fn = (EditText)findViewById(R.id.fn);
                ln = (EditText)findViewById(R.id.ln);
                age = (EditText)findViewById(R.id.age);
                address = (EditText)findViewById(R.id.address);
                myid.setText(separated[0]);
                fn.setText(separated[1].substring(1));
                ln.setText(separated[2].substring(1));
                img.setText(separated[6].substring(1));
                String value= img.getText().toString();
                int finalValue=Integer.parseInt(value);
                if(finalValue==1){
                    userImg.setImageResource(R.drawable.img1);
                }else if(finalValue==2){
                    userImg.setImageResource(R.drawable.img2);
                }else if(finalValue==3){
                    userImg.setImageResource(R.drawable.img3);
                }else if(finalValue==4){
                    userImg.setImageResource(R.drawable.img4);
                }
                if(separated[3].equals(" Male")){
                    spinner1.setSelection(0);
                }else{
                    spinner1.setSelection(1);
                }
                age.setText(separated[4].substring(1));
                address.setText(separated[5].substring(1));
                //productRec.setText(separated[6]);

            }
        });


        //NON COMPLETED TASKS LIST ONCLICK EDIT FUNCTIONALITY
        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)//when someone clicks on a tasks record in Edit Friend
            {
                String item = (String) listTasks.getAdapter().getItem(position);
                String[] separated = item.split(",");
                //Toast.makeText(getApplicationContext(), "ID: " + separated[0] + " selected", Toast.LENGTH_LONG).show();
                int MyID = Integer. parseInt(separated[0]);
                hideAll();
                saveTask.setVisibility(View.VISIBLE);
                homeButton.setVisibility(View.VISIBLE);
                prevButton.setVisibility(View.VISIBLE);
                deleteTask.setVisibility(View.VISIBLE);
                addTaskLayout.setVisibility(View.VISIBLE);
                createTask.setVisibility(View.GONE);
                response.setText("Edit Task Information");
                taskID.setText(separated[0]);
                taskName.setText(separated[1].substring(1));
                locationName.setText(separated[2].substring(1));
                if(separated[3].equals(" Not Completed")){
                    statusSpinner.setSelection(0);
                }else{
                    statusSpinner.setSelection(1);
                }
                //productRec.setText(separated[6]);

            }
        });
        //COMPLETED TASKS LIST ONCLICK EDIT FUNCTIONALITY
        listTasks2.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)//when someone clicks on a tasks record in Edit Friend
            {
                String item = (String) listTasks2.getAdapter().getItem(position);
                String[] separated = item.split(",");
                //Toast.makeText(getApplicationContext(), "ID: " + separated[0] + " selected", Toast.LENGTH_LONG).show();
                int MyID = Integer. parseInt(separated[0]);
                hideAll();
                saveTask.setVisibility(View.VISIBLE);
                homeButton.setVisibility(View.VISIBLE);
                prevButton.setVisibility(View.VISIBLE);
                deleteTask.setVisibility(View.VISIBLE);
                addTaskLayout.setVisibility(View.VISIBLE);
                createTask.setVisibility(View.GONE);
                response.setText("Edit Task Information");
                taskID.setText(separated[0]);
                taskName.setText(separated[1].substring(1));
                locationName.setText(separated[2].substring(1));
                if(separated[3].equals(" Not Completed")){
                    statusSpinner.setSelection(0);
                }else{
                    statusSpinner.setSelection(1);
                }
                //productRec.setText(separated[6]);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.addFriend:

                insertRec();
                break;
            case R.id.editFriend:

                showRec();
                break;
            case R.id.addTask:

                addTask();
                break;
            case R.id.viewTasks:

                viewTask();
                break;
            case R.id.addEvent:

                addEvent();
                break;
            case R.id.viewEvents:

                viewEvent();
                break;
//            case R.id.remove_rows:
//
//                removeRecs();
//                break;
            /*case R.id.deleteMultipleEvents:
                removeStu();
                break;*/
        }
        //return true;
        return super.onOptionsItemSelected(item);
    }
    public void hideAll(){
        img.setVisibility(View.GONE);
        ch1.setVisibility(View.GONE);
        ch2.setVisibility(View.GONE);
        ch3.setVisibility(View.GONE);
        ch4.setVisibility(View.GONE);
        userImg.setVisibility(View.GONE);
        eventID.setVisibility(View.GONE);
        eventName.setVisibility(View.GONE);
        eventDate.setVisibility(View.GONE);
        eventTime.setVisibility(View.GONE);
        eventLocation.setVisibility(View.GONE);
        flag.setVisibility(View.GONE);
        createEvent.setVisibility(View.GONE);
        addEventLayout.setVisibility(View.GONE);
        listEvents.setVisibility(View.GONE);
        TextNotComplete.setVisibility(View.GONE);
        TextComplete.setVisibility(View.GONE);
        saveTask.setVisibility(View.GONE);
        deleteTask.setVisibility(View.GONE);
        listTasks.setVisibility(View.GONE);
        listTasks2.setVisibility(View.GONE);
        addTaskLayout.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        prevButton.setVisibility(View.GONE);
        button12.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        addLayout.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        productRec.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        addButton.setVisibility(View.GONE);
    }//Function to hide all elements
    public void addEvent(){
        previousPage=currentPage;
        currentPage=8;
        hideAll();
        eventName.setVisibility(View.VISIBLE);
        eventDate.setVisibility(View.VISIBLE);
        eventTime.setVisibility(View.VISIBLE);
        eventLocation.setVisibility(View.VISIBLE);
        addEventLayout.setVisibility(View.VISIBLE);
        flag.setVisibility(View.VISIBLE);
        createEvent.setVisibility(View.VISIBLE);
        datedate.setText("Date:");
        timetime.setText("Time:");
        eventName.setText("");
        eventLocation.setText("");
        flag.setText("No");
        response.setText("Add new Event Information: \n");
        addEventLayout.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
    }
    public void createEvent(View view){
        datedate.setText(eventDate.getDayOfMonth()+"/"+ (eventDate.getMonth() + 1)+"/"+eventDate.getYear());
        timetime.setText(eventTime.getHour()+":"+ eventTime.getMinute());
        recInserted = eventManager.addRow( eventName.getText().toString(), datedate.getText().toString(), timetime.getText().toString() , eventLocation.getText().toString());
        addEventLayout.setVisibility(View.GONE);
        if (recInserted) {
            response.setText("successfully inserted event into database");
        }
        else {
            response.setText("Sorry, errors when inserting to DB");
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        eventManager.close();
        //id.setText("");
        eventName.setText("");
        eventLocation.setText("");
        flag.setText("");
    }
    public boolean viewEvent(){
        //removeStu();
        previousPage=currentPage;
        currentPage=9;
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        listEvents.setVisibility(View.VISIBLE);
        button12.setVisibility(View.VISIBLE);
        eventManager.openReadable();
        ArrayList<String> tableContent = eventManager.retrieveRows();
        adapter69 = new CustomAdapter(this, tableContent);
        listEvents.setAdapter(adapter69);
        response.setText("The rows in the Events table are: \n");
        return true;

    }
    public void addTask(){
        previousPage=currentPage;
        currentPage=3;
        hideAll();
        createTask.setVisibility(View.VISIBLE);
        taskName.setText("");
        locationName.setText("");
        statusSpinner.setSelection(0);
        response.setText("Add new Task Information: \n");
        addTaskLayout.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
    }
    public void createTask(View view){
        recInserted = taskManager.addRow( taskName.getText().toString(), locationName.getText().toString(), String.valueOf(statusSpinner.getSelectedItem()));
        addTaskLayout.setVisibility(View.GONE);
        if (recInserted) {
            response.setText("successfully inserted task into database");
        }
        else {
            response.setText("Sorry, errors when inserting to DB");
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        taskManager.close();
        //id.setText("");
        taskName.setText("");
        locationName.setText("");
        statusSpinner.setSelection(0);
    }
    public void viewTask(){
        previousPage=currentPage;
        currentPage=4;
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        listTasks.setVisibility(View.VISIBLE);
        listTasks2.setVisibility(View.VISIBLE);
        TextNotComplete.setVisibility(View.VISIBLE);
        TextComplete.setVisibility(View.VISIBLE);
        taskManager.openReadable();
        //Non completed tasks
        ArrayList<String> tableContent = taskManager.getNonCompleted();
        response.setText("The rows in the To Do List table are: \n");
        String info = "";
        for (int i = 0; i < tableContent.size(); i++) {
            info += tableContent.get(i) + "\n";
        }
        CustomAdapterFRIENDS adapter2 = new CustomAdapterFRIENDS(this, tableContent);
        listTasks.setAdapter(adapter2);

        //completed tasks
        ArrayList<String> tableContent2 = taskManager.getCompleted();
        response.setText("The rows in the To Do List table are: \n");
        String info1 = "";
        for (int i = 0; i < tableContent2.size(); i++) {
            info1 += tableContent2.get(i) + "\n";
        }
        CustomAdapterFRIENDS adapter3 = new CustomAdapterFRIENDS(this, tableContent2);
        listTasks2.setAdapter(adapter3);
    }
    public boolean insertRec() {
        previousPage=currentPage;
        currentPage=1;
        fn = (EditText)findViewById(R.id.fn);
        ln = (EditText)findViewById(R.id.ln);

        age = (EditText)findViewById(R.id.age);
        address = (EditText)findViewById(R.id.address);
        fn.setText("");
        ln.setText("");
        spinner1.setSelection(0);
        age.setText("");
        address.setText("");
        img.setText("");


        hideAll();
        img.setVisibility(View.VISIBLE);
        //cancelButton.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.VISIBLE);
        response.setText("Enter information of the new Friend");

        return true;
    }
    public boolean showRec() {
        previousPage=currentPage;
        currentPage=2;
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        list.setVisibility(View.VISIBLE);
        productRec.setVisibility(View.VISIBLE);

        mydManager.openReadable();
        ArrayList<String> tableContent = mydManager.retrieveRows();
        response.setText("The rows in the Friends table are: \n");
        String info = "";
        for (int i = 0; i < tableContent.size(); i++) {
            info += tableContent.get(i) + "\n";
        }

        CustomAdapterFRIENDS adapter2 = new CustomAdapterFRIENDS(this, tableContent);
        list.setAdapter(adapter2);
        //check1.setVisibility(View.GONE);

//        productRec.setText(info);
//        System.out.println(info);
        //mydManager.close();
        return true;
    }
    public boolean removeRecs() {
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        mydManager.clearRecords();
        response.setText("All Records are removed");
        productRec.setText("");
        return true;
    }
    public boolean removeStu() {//removes multiple events using checkboxes
        saveButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        button12.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.GONE);
        productRec.setVisibility(View.GONE);
        eventManager.openReadable();
        ArrayList<String> tableContent = eventManager.retrieveRows();
        adapter1 = new CustomAdapter(this, tableContent);
        list.setAdapter(adapter1);
        response.setText("The rows in the Friends table are: \n");
//        for (int i = 0; i < tableContent.size(); i++) {
//       info += tableContent.get(i) + "\n";
//        }
        //productRec.setText(info);
        //System.out.println(info);
        //mydManager.close();
        return true;
    }
    public void Submit (View v) {
        boolean[] checkboxes = adapter69.getCheckBoxState();
        ArrayList<String> tmp = new ArrayList<String>();
        String tmp2;
        ArrayList<String> store = new ArrayList<String>();
        //gets which checkbox was selected
        for (int i = 0; i < listEvents.getCount(); i++) {
            if (checkboxes[i] == true) {
                tmp.add(adapter69.getName(i));// list.getAdapter().getItem(i).toString();
            }
        }
        Context context = getApplicationContext();
        CharSequence text = "Deleted Records!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //seperates the student id from the string and stores into array
        while(!tmp.isEmpty()) {
            tmp2 = tmp.get(0);
            tmp.remove(0);
            String[] words = tmp2.split("\\s+");
            store.add(words[0]);
        }
        eventManager.deleteEntries(store);
        listEvents.setVisibility(View.GONE);
        listEvents.setVisibility(View.VISIBLE);
        viewEvent();

    }
    public void home(View view){
        previousPage=currentPage;
        currentPage=0;
        hideAll();
        response.setVisibility(View.VISIBLE);
        response.setText("Welcome to Assignment 1 \n This is the Home Page \n Press MENU button to display menu");
    }
    public void previous(View view){
        switch(previousPage){
            case 0:
                previousPage=currentPage;
                currentPage=0;
                hideAll();
                response.setVisibility(View.VISIBLE);
                response.setText("Welcome to Assignment 1 \n This is the Home Page \n Press MENU button to display menu");
                break;
            case 1:
                insertRec();
                break;
            case 2:
                showRec();
                break;
            case 3:
                addTask();;
                break;
            case 4:
                viewTask();
                break;
        }
    }
    public void updateRec(View view){
        //Toast.makeText(getApplicationContext(),  Integer.parseInt(myid.getText().toString()) + fn.getText().toString() + ln.getText().toString() + String.valueOf(spinner1.getSelectedItem()) + age.getText().toString() + address.getText().toString(), Toast.LENGTH_LONG).show();
        mydManager.updateRecord(Integer.parseInt(myid.getText().toString()), fn.getText().toString(), ln.getText().toString(), String.valueOf(spinner1.getSelectedItem()), Integer.parseInt(age.getText().toString()), address.getText().toString(), img.getText().toString());
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        response.setText("Updated Friend Information");
    }
    public void updateTask(View view){
        //Toast.makeText(getApplicationContext(),  Integer.parseInt(myid.getText().toString()) + fn.getText().toString() + ln.getText().toString() + String.valueOf(spinner1.getSelectedItem()) + age.getText().toString() + address.getText().toString(), Toast.LENGTH_LONG).show();
        taskManager.updateRecord(Integer.parseInt(taskID.getText().toString()), taskName.getText().toString(), locationName.getText().toString(), String.valueOf(statusSpinner.getSelectedItem()));
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        response.setText("Updated Task Information");
    }
    public void delRec(View view){
        mydManager.deleteFriend(Integer.parseInt(myid.getText().toString()));
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        response.setText("Deleted Friend Record");
    }
    public void delTask(View view){
        taskManager.deleteFriend(Integer.parseInt(taskID.getText().toString()));
        hideAll();
        homeButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        response.setText("Deleted Task Record");
    }
    public void setImg1(View view){
        img.setText("1");
        userImg.setImageResource(R.drawable.img1);
    }
    public void setImg2(View view){
        img.setText("2");
        userImg.setImageResource(R.drawable.img2);
    }
    public void setImg3(View view){
        img.setText("3");
        userImg.setImageResource(R.drawable.img3);
    }
    public void setImg4(View view){
        img.setText("4");
        userImg.setImageResource(R.drawable.img4);
    }
}
