package fg47942.android.fer.hr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import fg47942.android.fer.hr.operations.AddOperation;
import fg47942.android.fer.hr.operations.DivOperation;
import fg47942.android.fer.hr.operations.IOperation;
import fg47942.android.fer.hr.operations.MulOperation;
import fg47942.android.fer.hr.operations.SubOperation;


/**
 * Start activity for program Calculus. User enters two numbers and executes selected operation
 * over them. This activty displays a result of operation and error if operation was unsuccessful.
 *
 * @author Gulan
 * @version 1.0
 */
public class HostActivity extends AppCompatActivity {
    /**
     * First value representation.
     */
    public static final String EXTRAS_VALA = "VALA";
    /**
     * Second value representation.
     */
    public static final String EXTRAS_VALB = "VALB";
    /**
     * Operation representation.
     */
    public static final String OPERATION = "OPERATION";
    /**
     * Result forward mail.
     */
    private static final String EMAIL = "ana.baotic@infinum.hr";
    /**
     * User ID.
     */
    private static final String JMBAG = "0036479428";
    /**
     * Request code.
     */
    private static final int REQ_CODE = 734;
    /**
     * First value.
     */
    private Double valueA;
    /**
     * Second value.
     */
    private Double valueB;
    /**
     * Result.
     */
    private Double result;
    /**
     * Selected operation.
     */
    private IOperation processedOperation;

    /**
     * Result text view.
     */
    private TextView tvResult;
    /**
     * Error text view.
     */
    private TextView tvError;
    /**
     * First variable.
     */
    private EditText etVariableA;
    /**
     * Second variable.
     */
    private EditText etVariableB;
    /**
     * Operation chooser.
     */
    private Spinner spinner;
    /**
     * Operation error.
     */
    private String errorMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_host);
        spinner = (Spinner) findViewById(R.id.spinner);
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvError = (TextView) findViewById(R.id.tvError);
        etVariableA = (EditText) findViewById(R.id.etFirstVariable);
        etVariableB = (EditText) findViewById(R.id.etSecondVariable);
        fillSpinner();

        Button btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String varA = etVariableA.getText().toString();
                    String varB = etVariableB.getText().toString();

                    valueA = null;
                    valueB = null;
                    try {
                        valueA = Double.parseDouble(varA);
                        valueB = Double.parseDouble(varB);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(
                        HostActivity.this,
                        CalculusActivity.class);

                    processedOperation = (IOperation) spinner.getSelectedItem();
                    intent.putExtra(OPERATION, processedOperation);
                    intent.putExtra(EXTRAS_VALA, valueA);
                    intent.putExtra(EXTRAS_VALB, valueB);
                    startActivityForResult(intent, REQ_CODE);
                }
            });

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE && resultCode == RESULT_OK && data != null) {
            result = data.getExtras()
                .getDouble(CalculusActivity.EXTRAS_RESULT, 0);
            tvResult.setText(String.valueOf(result));
            errorMessage = null;
            tvError.setText("");
        } else if (requestCode == REQ_CODE && resultCode == RESULT_CANCELED && data != null) {
            errorMessage = data.getExtras().getString(CalculusActivity.ERROR, "");
            tvResult.setText(getString(R.string.unknown));
            tvError.setText(errorMessage);
        }
    }

    /**
     * Fills spinner with supported math operations.
     */
    private void fillSpinner() {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            new IOperation[]{
                new AddOperation(),
                new SubOperation(),
                new MulOperation(),
                new DivOperation()
            });
        spinner.setAdapter(spinnerAdapter);
    }

    /**
     * Sends an email to selected user with current operation result as body of email.“
     */
    private void sendEmail() {
        if (processedOperation == null) {
            Toast.makeText(HostActivity.this, getString(R.string.no_operation_error), Toast
                .LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL});
        i.putExtra(Intent.EXTRA_SUBJECT, JMBAG + ": " + getString(R.string.report));

        if (errorMessage == null) {
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.result) +
                " " + valueA + " " +
                processedOperation + " " +
                valueB + " " + getString(R.string.to_be) +
                " " + result + ".");
        } else {
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.execution_error)
                + " " + errorMessage);
        }

        try {
            startActivity(Intent.createChooser(i, getString(R.string.send_mail)));
        } catch (Exception ex) {
            Toast.makeText(HostActivity.this, getString(R.string.no_email_clients), Toast
                .LENGTH_SHORT).show();
        }
    }
}