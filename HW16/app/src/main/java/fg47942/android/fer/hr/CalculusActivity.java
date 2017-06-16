package fg47942.android.fer.hr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fg47942.android.fer.hr.operations.DivOperation;
import fg47942.android.fer.hr.operations.IOperation;

/**
 * CalculusActivity is activity which displays a label with computed result over given numbers
 * and operation in HostActivity, and button for returning in HostActivity.
 *
 * @author Gulan
 * @version 1.0
 */
public class CalculusActivity extends AppCompatActivity {
    /**
     * Intent result constant.
     */
    public static final String EXTRAS_RESULT = "result";
    /**
     * Intent error constant.
     */
    public static final String ERROR = "error";
    /**
     * Result label.
     */
    private TextView tvLabel;
    /**
     * Return button.
     */
    private Button btnReturn;
    /**
     * Operation result.
     */
    private double result;
    /**
     * Error message (if error exists).
     */
    private String errorMessage = null;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        tvLabel = (TextView) findViewById(R.id.tvLabel);
        btnReturn = (Button) findViewById(R.id.btnReturn);
        processParameters();
        btnReturn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    if (errorMessage != null) {
                        i.putExtra(ERROR, errorMessage);
                        setResult(RESULT_CANCELED, i);
                    } else {
                        i.putExtra(EXTRAS_RESULT, result);
                        setResult(RESULT_OK, i);
                    }
                    finish();
                }
            });
    }

    /**
     * This method processes parameters (input variables and operation) given in HostActivity.
     * Checks given numbers and operation for error, and if there is no errors, executes given
     * operation over given numbers.
     */
    private void processParameters() {
        Bundle map = getIntent().getExtras();

        Double valA = (Double) map.get(HostActivity.EXTRAS_VALA);
        Double valB = (Double) map.get(HostActivity.EXTRAS_VALB);
        if (valA == null || valB == null) {
            errorMessage = getString(R.string.number_error);
            tvLabel.setText(errorMessage);
            return;
        }

        IOperation operation = (IOperation) map.get(HostActivity.OPERATION);
        if (operation == null) {
            errorMessage = getString(R.string.operation_error);
            tvLabel.setText(errorMessage);
            return;
        } else if (operation instanceof DivOperation && Math.abs(valB) == 0.0) {
            errorMessage = getString(R.string.division_by_zero);
            tvLabel.setText(errorMessage);
            return;
        }

        result = operation.calculate(valA, valB);
        tvLabel.setText(getString(R.string.operation_result) + " " + result);
    }
}
