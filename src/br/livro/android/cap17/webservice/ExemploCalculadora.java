package br.livro.android.cap17.webservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Calculadora em Android, mas faz a soma chamando um Servlet que está no
 * servidor
 * 
 * @author ricardo
 * 
 */
public class ExemploCalculadora extends Activity implements OnClickListener, Runnable {
	private static final String CATEGORIA = "livro";
	private Handler handler = new Handler();
	private ProgressDialog dialog;

	// URL do Servlet. IP 10.0.2.2 é para o localhost
	// Faça deploy da aplicação web com Web Service no seu servidor: "xfire.war"
	private static final String URL = "http://10.0.2.2:8080/livro_android_xfire/services/Calculadora";

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.soma);
		Button b = (Button) findViewById(R.id.btSomar);
		b.setOnClickListener(this);
	}

	public void onClick(View view) {
		dialog = ProgressDialog.show(this, "Exemplo", "Calculando com web service, por favor aguarde...", false, true);
		// Faz a soma em outra Thread
		new Thread(this).start();
	}

	public void run() {
		EditText textn1 = (EditText) findViewById(R.id.n1);
		EditText textn2 = (EditText) findViewById(R.id.n2);
		final TextView textSoma = (TextView) findViewById(R.id.soma);

		int n1 = Integer.parseInt(textn1.getText().toString());
		int n2 = Integer.parseInt(textn2.getText().toString());

		try {
			Calculadora calculadora = new Calculadora(URL);
			final String soma = calculadora.somar(n1, n2);
			handler.post(new Runnable() {
				public void run() {
					textSoma.setText("Soma: " + soma);
					Log.i(CATEGORIA, String.valueOf(soma));
					textSoma.setVisibility(View.VISIBLE);
				}
			});
		} catch (Exception e) {
			Log.e(CATEGORIA, e.getMessage(), e);
		} finally {
			dialog.dismiss();
		}
	}
}