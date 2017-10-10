package dk.hjalmarthulstrup.loenudregner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Scanner scan = new Scanner(System.in);

    public static String[] Udregner(double RATE, double lørtillæg, double søntillæg,
                                    double aftentillæg, double hours, double lørdagstimer,
                                    double søndagstimer, double aftenstimer, double fradrag, double pns) {

            double pension = 1 - (pns/100);
            double tillæg = (lørtillæg * lørdagstimer) + (søntillæg * søndagstimer) + (aftentillæg * aftenstimer);
            double pay = hours * RATE;
            double payout = pay + tillæg;
            double payoutpension = payout * pension;
            double payminam = payoutpension * 0.92;
            double payminfradrag = payminam - fradrag;
            double payminamskatfradrag = payminfradrag * 0.61;
            double payminskatplusfradrag = payminamskatfradrag + fradrag;
            NumberFormat fmt = NumberFormat.getCurrencyInstance();

        if (payoutpension <= fradrag) {
                return new String[]{"Udbetaling efter pension: " + fmt.format(payoutpension), "Udbetaling efter AM-bidrag: " + fmt.format(payminam)};
            } else {
                return new String[]{"Udbetaling efter pension men før skat: " + fmt.format(payoutpension),
                        "Udbetaling efter AM-bidrag: " + fmt.format(payminam),
                        "Udbetaling efter SKAT og AM-Bidrag: " + fmt.format(payminamskatfradrag),
                        "Udbetaling efter SKAT og AM-Bidrag og fradrag lagt på: " + fmt.format(payminskatplusfradrag)};
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        ArrayList<String> values = new ArrayList<>();
//        int idList[] = new int[]{R.id.editText5, R.id.editText, R.id.editText3,
//                R.id.editText8, R.id.editText22, R.id.editText28};

        //for(int i = 0; i < 6; i++){

        //}




        //Get the text file
        File file = new File(getFilesDir(), "values.txt");

        //Read text from file
        StringBuilder text = new StringBuilder();

        ArrayList<String> values = new ArrayList<>();
        //String[] stringarr = new String[6];

        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                //text.append('\n');
            }

            br.close();


        }
        catch (IOException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setCancelable(true);

            builder.setTitle("Fejl!");
            builder.setMessage("Der er ingen values i filen. Hvis det er første gang du åbner appen skal du blot" +
                    " indtaste dine værdier og udregne din løn, og så har den gemt værdierne næste gang du åbner appen");

            //Laver negativ "Tilbage" knap
            builder.setNegativeButton("Tilbage", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.show();
        }


        //try {
            //BufferedReader br = new BufferedReader(new FileReader(file));
            //String line;




//            while ((line = br.readLine()) != null) {
//                text.append(line);
//                text.append('\n');
//            }

            //br.close();
       // }
//        catch (IOException e) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//            builder.setCancelable(true);
//
//            builder.setTitle("Fejl!");
//            builder.setMessage("Der er ingen values i filen.");
//
//            //Laver negativ "Tilbage" knap
//            builder.setNegativeButton("Tilbage", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                }
//            });
//
//            builder.show();
//        }

        try{
            int previ = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    values.add(text.substring(previ, i).replaceAll(" ", ""));
                    previ = i;
                }
            }

            /*String str1 = values.get(0);
            String str2 = values.get(1);
            String str3 = values.get(2);
            String str4 = values.get(3);
            String str5 = values.get(4);
            String str6 = values.get(5);*/

            //Find the view by its id
            EditText timl = (EditText)findViewById(R.id.editText5);
            EditText lørtil = (EditText)findViewById(R.id.editText);
            EditText søntil = (EditText)findViewById(R.id.editText3);
            EditText afttil = (EditText)findViewById(R.id.editText8);
            EditText frad = (EditText)findViewById(R.id.editText22);
            EditText pens = (EditText)findViewById(R.id.editText28);

            //Set the text
            //timl.setText(text);
            timl.setText(values.get(0));
            lørtil.setText(values.get(1));
            søntil.setText(values.get(2));
            afttil.setText(values.get(3));
            frad.setText(values.get(4));
            pens.setText(values.get(5));
        }
        catch (Throwable throwable){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Fejl!");
            builder.setMessage("For loop fail. Line #147. Ignorer hvis det er første gang du åbner appen.");
            //Laver negativ "Tilbage" knap
            builder.setNegativeButton("Tilbage", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }


        final EditText timeløn = (EditText)findViewById(R.id.editText5);
        final EditText lørtillæg = (EditText)findViewById(R.id.editText);
        final EditText søntillæg = (EditText)findViewById(R.id.editText3);
        final EditText aftentillæg = (EditText)findViewById(R.id.editText8);
        final EditText timer = (EditText)findViewById(R.id.editText17);
        final EditText lørtimer = (EditText)findViewById(R.id.editText19);
        final EditText søntimer = (EditText)findViewById(R.id.editText20);
        final EditText aftentimer = (EditText)findViewById(R.id.editText21);
        final EditText fradrag = (EditText)findViewById(R.id.editText22);
        final EditText pensio = (EditText)findViewById(R.id.editText28);
        final Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //ArrayList<String> valuesarr = new ArrayList<>();
                //double[] idlist = new double[]{Double.parseDouble(timeløn.getText().toString()), Double.parseDouble(lørtillæg.getText().toString()),
                  //      Double.parseDouble(søntillæg.getText().toString()),
                    //    Double.parseDouble(aftentillæg.getText().toString()), Double.parseDouble(fradrag.getText().toString()),
                      //  Double.parseDouble(pensio.getText().toString())};




                if(lørtillæg.getText().toString().equals("") || lørtillæg.getText().toString().equals("") ||
                        søntillæg.getText().toString().equals("") || aftentillæg.getText().toString().equals("") ||
                        timer.getText().toString().equals("") || lørtimer.getText().toString().equals("") ||
                        søntimer.getText().toString().equals("") || aftentimer.equals("") ||
                        fradrag.getText().toString().equals("") || pensio.getText().toString().equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setCancelable(true);

                    builder.setTitle("Hov vent!");
                    builder.setMessage("Du skal skrive en værdi i alle felterne.");

                    //Laver negativ "Tilbage" knap
                    builder.setNegativeButton("Tilbage", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.show();
                }
                else {


                    try{
                       /* PrintWriter writer = new PrintWriter("Android/data/dk.hjalmarthusltrup.loenudregner/files/values.txt", "UTF-8");
                        writer.println(Double.parseDouble(timeløn.getText().toString()));
                        writer.println(Double.parseDouble(lørtillæg.getText().toString()));
                        writer.println(Double.parseDouble(søntillæg.getText().toString()));
                        writer.println(Double.parseDouble(aftentillæg.getText().toString()));
                        writer.println(Double.parseDouble(fradrag.getText().toString()));
                        writer.println(Double.parseDouble(pensio.getText().toString()));
                        writer.close();*/
                        File myFile = new File(getFilesDir(), "values.txt");
                        myFile.createNewFile();
                        FileOutputStream fOut = new FileOutputStream(myFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        myOutWriter.append(timeløn.getText().toString() + " ");
                        myOutWriter.append(lørtillæg.getText().toString() + " ");
                        myOutWriter.append(søntillæg.getText().toString() + " ");
                        myOutWriter.append(aftentillæg.getText().toString() + " ");
                        myOutWriter.append(fradrag.getText().toString() + " ");
                        myOutWriter.append(pensio.getText().toString() + " ");
                        myOutWriter.close();
                        fOut.close();
                    } catch (IOException e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setCancelable(true);

                        builder.setTitle("Fejl!");
                        builder.setMessage("File creation failed.");
                        //Laver negativ "Tilbage" knap
                        builder.setNegativeButton("Tilbage", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                    }
                    final String[] values = Udregner(Double.parseDouble(timeløn.getText().toString()),
                            Double.parseDouble(lørtillæg.getText().toString()),
                            Double.parseDouble(søntillæg.getText().toString()),
                            Double.parseDouble(aftentillæg.getText().toString()),
                            Double.parseDouble(timer.getText().toString()),
                            Double.parseDouble(lørtimer.getText().toString()),
                            Double.parseDouble(søntimer.getText().toString()),
                            Double.parseDouble(aftentimer.getText().toString()),
                            Double.parseDouble(fradrag.getText().toString()),
                            Double.parseDouble(pensio.getText().toString()));
                    setContentView(R.layout.activity_result);

                    if(values.length <= 2){
                        TextView out = (TextView) findViewById(R.id.textView);
                        out.setText(values[0] + "\n" + "\n" + values[1]);
                    }
                    else{
                        TextView out = (TextView) findViewById(R.id.textView);
                        out.setText(values[0] + "\n" + "\n" + values[1] + "\n" + "\n" + values[2] + "\n" + "\n" + values[3]);
                    }
                }


            }
        });

} }
