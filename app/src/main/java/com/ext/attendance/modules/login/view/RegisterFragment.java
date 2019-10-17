package com.ext.attendance.modules.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ext.attendance.BuildConfig;
import com.ext.attendance.R;
import com.ext.attendance.apputils.AndroidPermissions;
import com.ext.attendance.apputils.AppKeysInterface;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.dialogs.PickImageDialog;
import com.ext.attendance.interfaces.PickImageDialogInterface;
import com.ext.attendance.modules.login.viewmodels.RegisterViewModel;
import com.ext.attendance.prefs.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class RegisterFragment extends BaseFragment implements RegisterNavigator, View.OnClickListener, PickImageDialogInterface {
    private LoginBaseActivity activity;
    private Unbinder unbinder;
    private RegisterViewModel mRegisterViewModel;
    final Calendar myCalendar = Calendar.getInstance();
    private Session session;
    private PickImageDialog mPickImageDialog;
    private Uri mImageFileUri;
    private File mImageFile;

    @BindView(R.id.buttonRegister)
    Button registerButton;
    @BindView(R.id.etName)
    TextInputEditText NameTextInputEditText;
    @BindView(R.id.etMName)
    TextInputEditText mNameTextInputEditText;
    @BindView(R.id.etLName)
    TextInputEditText lNameTextInputEditText;
    @BindView(R.id.etUserName)
    TextInputEditText usernameTextInputEditText;
    @BindView(R.id.etContact)
    TextInputEditText contactTextInputEditText;
    @BindView(R.id.etEmail)
    TextInputEditText emailTextInputEditText;
    @BindView(R.id.etEmergencyContact)
    TextInputEditText emergencyContactTextInputEditText;
    @BindView(R.id.etPanNumber)
    TextInputEditText panNumberTextInputEditText;
    @BindView(R.id.etDateOfBirth)
    TextInputEditText dobTextInputEditText;
    @BindView(R.id.etDesignation)
    TextInputEditText designationTextInputEditText;
    @BindView(R.id.pbRegister)
    ProgressBar pbRegister;
    @BindView(R.id.profilePicImageView)
    ImageView profilePicImageView;
    @BindView(R.id.aadharDetailsTV)
    TextView aadharDetailsTextView;
    @BindView(R.id.scanAadharBtn)
    Button scanAadharButton;


    private String uid = "";
    private String name = "";
    private String gender = "";
    private String yob = "";
    private String co = "";
    private String house = "";
    private String street;
    private String lm;
    private String loc;
    private String vtc;
    private String po;
    private String dist;
    private String subdist;
    private String state;
    private String pc;
    private String dob;
    private String dobGuess;
    String adharDetails = "";

    @Override
    protected int layoutRes() {
        return R.layout.register_profile_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        session = new Session(activity);

        if (session.getProfilePicUrl().length() > 0) {
            Picasso.get().load(BuildConfig.BASE_URL + "images/" + session.getProfilePicUrl()).fit().centerCrop().placeholder(R.drawable.ic_image_size_select_actual).into(profilePicImageView);
        }
        registerButton.setOnClickListener(this);
        dobTextInputEditText.setOnClickListener(this);
        scanAadharButton.setOnClickListener(this);
        profilePicImageView.setOnClickListener(this);

        activity.toolbar.setTitle("");
        activity.toolbarTitle.setText("Register");

        mRegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        mRegisterViewModel.setNavigator(this);

        mRegisterViewModel.getGeneralResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GeneralResponse>() {
            @Override
            public void onChanged(GeneralResponse generalResponse) {
                if (generalResponse != null && generalResponse.getStatus() == 200) {
                    Toast.makeText(activity, "Registered Success", Toast.LENGTH_LONG).show();
                    activity.onBackPressed();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LoginBaseActivity) context;
    }

    @Override
    public void handleError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            showToast(getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            showToast("Request Time out.");
        } else if (e instanceof IOException) {
            showToast("Network Error.");
        } else {
            showToast(e.getMessage());
        }
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void register() {

    }

    @Override
    public void loadProgressBar(boolean showProgress) {
        if (pbRegister != null) {
            pbRegister.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                @SuppressLint("HardwareIds")
                String androidId = Settings.Secure.getString(activity.getContentResolver(),
                        Settings.Secure.ANDROID_ID);


                if (mRegisterViewModel.inputValidation(Objects.requireNonNull(NameTextInputEditText.getText()).toString(),
                        lNameTextInputEditText.getText().toString(), emailTextInputEditText.getText().toString(),
                        contactTextInputEditText.getText().toString())) {

                    if (mRegisterViewModel.getJsonObjectMutableLiveData().getValue() != null) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty(AppKeysInterface.FIRST_NAME, NameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.MIDDLE_NAME, mNameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.LAST_NAME, lNameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.EMAIL, emailTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.USERNAME, usernameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.CONTACT, contactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.EMERGENCY_CONTACT, emergencyContactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.PAN_NO, panNumberTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DOB, dobTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DEVICE_ID, androidId);
                        jsonObject.addProperty(AppKeysInterface.DEVICE_TOKEN, session.getDeviceToken());//device token used for push notificstion
                        jsonObject.addProperty(AppKeysInterface.DEVICE_TYPE, AppKeysInterface.ANDROID);

                        Timber.d("Register_DATA:%s", new Gson().toJson(jsonObject));
                        mRegisterViewModel.getJsonObjectMutableLiveData().setValue(jsonObject);
                        mRegisterViewModel.employeeRegister();
                    }
                } else {
                    Toast.makeText(activity, "Please Enter All Required Fields!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.etDateOfBirth:
                dateDialog();
                break;
            case R.id.scanAadharBtn:
                startActivityForResult(new Intent(activity, BarcodeScannerActivity.class), 1002);
                break;
            case R.id.profilePicImageView:
                openDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1002) {
                assert data != null;
                adharDetails = ScanResult(String.valueOf(data.getStringExtra("barcode")));
                aadharDetailsTextView.setText(adharDetails);
            } else {
                if (requestCode == UCrop.REQUEST_CROP) {
                    updateImageVisibilityInGallery();
                    displayPickedImage(mImageFile);

                } else if (requestCode == UCrop.RESULT_ERROR) {
                    onResultCancelled();
                } else {
                    if (mPickImageDialog == null) {
                        mPickImageDialog = new PickImageDialog(activity);
                        mPickImageDialog.delegate = this;
                        mPickImageDialog.resetFiles(mImageFileUri, mImageFile);
                    }
                    mPickImageDialog.onActivityResult(requestCode, data);
                }
            }
        } else {
            if (mPickImageDialog != null)
                mPickImageDialog.onResultCancelled();
        }
    }

    public boolean onResultCancelled() {
        return mImageFile != null && mImageFile.delete();
    }

    private void openDialog() {
        if (mPickImageDialog == null) {
            mPickImageDialog = new PickImageDialog(activity);
            mPickImageDialog.delegate = this;
        }

        checkGalleryPermission();
    }

    private void checkGalleryPermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (!(AndroidPermissions.getInstance().checkSDCardReadPermission(activity))) {
                AndroidPermissions.getInstance().displayCameraAndGalleryPermissionAlert(activity);

            } else {
                mPickImageDialog.showDialog();
            }
        } else {
            mPickImageDialog.showDialog();
        }

    }

    private void updateLaFbel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        dobTextInputEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateImageVisibilityInGallery() {
        // only for kitkat and newer versions
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        if (mImageFileUri != null) {
            intent.setData(mImageFileUri);
        }
        activity.sendBroadcast(intent);

    }

    public void dateDialog() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog picker = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dobTextInputEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();

    }

    public String ScanResult(String input) {

        String rawString = input;
        // copied from http://www.java-samples.com/showtutorial.php?tutorialid=152
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom;
        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Replace </?xml... with <?xml...
            if (input.startsWith("</?")) {
                input = input.replaceFirst("</\\?", "<?");
            }
            // Replace <?xml...?"> with <?xml..."?>
            input = input.replaceFirst("^<\\?xml ([^>]+)\\?\">", "<?xml $1\"?>");
            //parse using builder to get DOM representation of the XML file
            dom = db.parse(new ByteArrayInputStream(input.getBytes("UTF-8")));


        } catch (ParserConfigurationException | SAXException | IOException e) {
            dom = null;
        }
        if (dom != null) {
            Node node = dom.getChildNodes().item(0);
            NamedNodeMap attributes = node.getAttributes();
            uid = getAttributeOrEmptyString(attributes, "uid");
            name = getAttributeOrEmptyString(attributes, "name");
            String rawGender = getAttributeOrEmptyString(attributes, "gender");
            try {
                rawGender = formatGender(rawGender);
            } catch (ParseException e) {
                System.err.println("Expected gender to be one of m, f, male, female; got " + rawGender);
            }
            gender = rawGender;
            yob = getAttributeOrEmptyString(attributes, "yob");
            co = getAttributeOrEmptyString(attributes, "co");
            house = getAttributeOrEmptyString(attributes, "house");
            street = getAttributeOrEmptyString(attributes, "street");
            lm = getAttributeOrEmptyString(attributes, "lm");
            loc = getAttributeOrEmptyString(attributes, "loc");
            vtc = getAttributeOrEmptyString(attributes, "vtc");
            po = getAttributeOrEmptyString(attributes, "po");
            dist = getAttributeOrEmptyString(attributes, "dist");
            subdist = getAttributeOrEmptyString(attributes, "subdist");
            state = getAttributeOrEmptyString(attributes, "state");
            pc = getAttributeOrEmptyString(attributes, "pc");
            String rawDob = getAttributeOrEmptyString(attributes, "dob");
            try {
                rawDob = formatDate(rawDob);
            } catch (ParseException e) {
                System.err.println("Expected dob to be in dd/mm/yyyy or yyyy-mm-dd format, got " + rawDob);
            }
            dob = rawDob;
        } else if (rawString.matches("\\d{12}")) {
            uid = rawString;
            name = "";
            gender = "";
            yob = "";
            co = "";
            house = "";
            street = "";
            lm = "";
            loc = "";
            vtc = "";
            po = "";
            dist = "";
            subdist = "";
            state = "";
            pc = "";
            dob = "";
        } else {
            uid = "";
            name = "";
            gender = "";
            yob = "";
            co = "";
            house = "";
            street = "";
            lm = "";
            loc = "";
            vtc = "";
            po = "";
            dist = "";
            subdist = "";
            state = "";
            pc = "";
            dob = "";
        }
        dobGuess = getDobGuess(dob, yob);
        if (name.length() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Adhar No: ");
            stringBuilder.append(uid);
            stringBuilder.append(",");
            stringBuilder.append(name);
            stringBuilder.append(",");
            stringBuilder.append(gender);
            stringBuilder.append(", Address: ");
            stringBuilder.append(house);
            stringBuilder.append(",");
            stringBuilder.append(street);
            stringBuilder.append(",");
            stringBuilder.append(lm);
            stringBuilder.append(",");
            stringBuilder.append(loc);
            stringBuilder.append(",");
            stringBuilder.append(vtc);
            stringBuilder.append(",");
            stringBuilder.append(po);
            stringBuilder.append(",");
            stringBuilder.append(dist);
            stringBuilder.append(",");
            stringBuilder.append(state);
            stringBuilder.append(",");
            stringBuilder.append(pc);
            //stringBuilder.append(",DOB:");
            //stringBuilder.append(dobGuess);
            adharDetails = stringBuilder.toString();
        }
        return adharDetails;
    }

    protected String formatDate(String rawDateString) throws ParseException {
        if (rawDateString.equals("")) {
            return "";
        }
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat[] possibleFormats = {
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())};
        Date date = null;
        ParseException parseException = null;
        for (SimpleDateFormat fromFormat : possibleFormats) {
            try {
                date = fromFormat.parse(rawDateString);
                break;
            } catch (ParseException e) {
                parseException = e;
            }
        }
        if (date != null) {
            return toFormat.format(date);
        } else if (parseException != null) {
            throw parseException;
        } else {
            throw new AssertionError("This code is unreachable");
        }
    }

    @NonNull
    protected String formatGender(String gender) throws ParseException {
        String lowercaseGender = gender.toLowerCase();
        if (lowercaseGender.equals("male") || lowercaseGender.equals("m")) {
            return "M";
        } else if (lowercaseGender.equals("female") || lowercaseGender.equals("f")) {
            return "F";
        } else if (lowercaseGender.equals("other") || lowercaseGender.equals("o")) {
            return "O";
        } else {
            throw new ParseException("404 gender not found", 0);
        }
    }

    @NonNull
    private String getDobGuess(String dob, String yob) {
        if (dob.equals("")) {
            Integer yearInt;
            try {
                yearInt = Integer.parseInt(yob);
            } catch (NumberFormatException e) {
                return "";
            }
            // June 1 of the year
            return Integer.toString(yearInt) + "-06-01";
        } else {
            return dob;
        }
    }

    private String getAttributeOrEmptyString(NamedNodeMap attributes, String attributeName) {
        Node node = attributes.getNamedItem(attributeName);
        if (node != null) {
            return node.getTextContent();
        } else {
            return "";
        }
    }

    @Override
    public void holdRecordingFile(Uri fileUri, File file, Uri uri, int crop) {
        this.mImageFileUri = fileUri;
        this.mImageFile = file;
        if (crop == 1) {
            Timber.d("displayPickedImage:%s", file);
            Picasso.get().load(file).fit().centerCrop().placeholder(R.drawable.ic_image_size_select_actual).into(profilePicImageView);

        }
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void displayPickedImage(File file) {
        Timber.d("displayPickedImage:%s", file);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRegisterViewModel != null) {
            mRegisterViewModel.clearViewModelData();
        }
    }

}