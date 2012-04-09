package br.livro.android.cap17.webservice;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * HttpTransporte customizado apenas para imprimir o XML do Envelope
 * 
 * Poderíamos ter implementado a requisição Http aqui, mas o HttpTransportSE já
 * é suficiente
 * 
 * @author ricardo
 * 
 */
public class LivroHttpTransport extends HttpTransportSE {
	private static final String CATEGORIA = "livro";

	public LivroHttpTransport(String s) {
		super(s);
	}

	@Override
	public void call(String s, SoapEnvelope soapenvelope) throws IOException, XmlPullParserException {
		// Apenas para logar o xml elemento envelope do SOAP
		byte bytes[] = createRequestData(soapenvelope);
		String envelope = new String(bytes);
		Log.i(CATEGORIA, "Envelope: " + envelope);

		super.call(s, soapenvelope);
	}
}
