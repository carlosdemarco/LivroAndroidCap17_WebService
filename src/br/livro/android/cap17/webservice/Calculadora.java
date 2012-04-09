package br.livro.android.cap17.webservice;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * Calculadora que acessa o WebService:
 * 
 * http://localhost/xfire/services/CalculadoraService?wsdl
 * 
 * no Android substituir o localhost por 10.0.2.2
 * 
 * http://localhost:8080/livro_android_xfire/services/Calculadora?wsdl
 * 
 * @author ricardo
 *
 */
public class Calculadora {
	private static final String TAG = "ID";
	private final String url;
	public Calculadora(String url) {
		this.url = url;
	}
	public String somar(int n1, int n2) throws IOException, XmlPullParserException {
		// Namespace e nome para o objeto SOAP
		SoapObject soap = new SoapObject("urn:Calculadora", "soma");

		// Adiciona os parâmetros para a soma
		soap.addProperty("n1", n1);
		soap.addProperty("n2", n2);

		// Cria o envelope com o objeto SOAP
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(soap);

		Log.i(TAG, "Chamando WebService: " + url);

		// Cria o HttpTransport para enviar os dados (SOAP)
		HttpTransportSE httpTransport = new LivroHttpTransport(url);

		// Faz a requisição
		httpTransport.call("", envelope);

		// Recupera o resultado
		Object soma = envelope.getResponse();

		Log.i(TAG,"Soma: " + soma);

		return soma.toString();
	}
}
