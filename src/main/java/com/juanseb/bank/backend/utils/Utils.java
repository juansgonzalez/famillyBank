package com.juanseb.bank.backend.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;
import com.juanseb.bank.backend.model.enumerado.TipoMovimiento;
import com.juanseb.bank.backend.pojo.ObjectImg;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.vaadin.flow.component.UI;

public class Utils {
	
	public static Optional<Usuario> getCurrentUser(UsuarioService usuarioService){
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    UserDetails userDetails = ((UserDetails)principal);
	    Optional<Usuario> usuarioActual = usuarioService.obtenerUsuarioByUsername(userDetails.getUsername());
	    return usuarioActual;
	}
	
	public static String getMonthForInt(int num) {
	        String month = "wrong";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (num >= 0 && num <= 11 ) {
	        month = months[num];
	    }
	    return month;
	}
  
	public static double obtenerIngresos(List<Movimiento> listaMovimientos) {
		 double ingreso = 0;
		 for (Iterator iterator = listaMovimientos.iterator(); iterator.hasNext();) {
			 Movimiento movimiento = (Movimiento) iterator.next();
			 if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
				 ingreso += movimiento.getCantidad();
			 }
		 }
		 return ingreso;
	}

	public static String obtenerSaldoDeMovimientosFormateado(List<Movimiento> listaMovimientos){
		double balance = 0;
		for (Iterator iterator = listaMovimientos.iterator(); iterator.hasNext();) {
			Movimiento movimiento = (Movimiento) iterator.next();
			if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
				balance += movimiento.getCantidad();
			}else {
				balance -= movimiento.getCantidad();
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(balance);
	}

	public static double obtenerSaldoDeMovimientos(List<Movimiento> listaMovimientos){
		double balance = 0;
		for (Iterator iterator = listaMovimientos.iterator(); iterator.hasNext();) {
			Movimiento movimiento = (Movimiento) iterator.next();
			if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
				balance += movimiento.getCantidad();
			}else {
				balance -= movimiento.getCantidad();
			}
		}
		return balance;
	}

	public static double obtenerGastos(List<Movimiento> listaMovimientos) {
		double gastos = 0;
		for (Iterator iterator = listaMovimientos.iterator(); iterator.hasNext();) {
			Movimiento movimiento = (Movimiento) iterator.next();
			if(movimiento.getTipo().equals(TipoMovimiento.GASTO)) {
				gastos += movimiento.getCantidad();
			}
		}
		return gastos;
	}

	public static String enmascararNumeroTarjeta(Long numeroTarjeta) {
		String numeroTarjetaString ;			
		if(numeroTarjeta != null) {
			numeroTarjetaString = String.valueOf(numeroTarjeta);
		}else {
			numeroTarjetaString = "";
		}
		
		String tarjetaMascarade = numeroTarjetaString.replaceAll("\\d(?=\\d{4})", "*");
		
		for (int i = 0; 8 < tarjetaMascarade.length(); i++) {
			tarjetaMascarade = tarjetaMascarade.substring(1);
		}
		
		return tarjetaMascarade;
		
	}
	
	public static Boolean isPrincipal(Usuario usuario) {
		Long idUsuarioPrincipal = (long) UI.getCurrent().getSession().getAttribute("idUsuarioPrincipal");
        if(usuario.getId().equals(idUsuarioPrincipal)) {
        	return true;
        }else {
        	return false;
        }
	}
	
	public static Double obtenerSaldoEnCuenta(Long idCuenta, Long idUsuario, UsuarioCuentaService usuarioCuentaService) {
		
		Usuario u = new Usuario();
		u.setId(idUsuario);
		
		Cuenta c = new Cuenta();
		c.setId(idCuenta);
		
		UsuarioCuentaId uc = new UsuarioCuentaId();
		uc.setCuenta(c);
		uc.setUsuario(u);
		UsuarioCuenta ucObtenido = null;
		if(usuarioCuentaService.obtenerDatosUsuarioCuenta(uc).isPresent()) {
			ucObtenido = usuarioCuentaService.obtenerDatosUsuarioCuenta(uc).get();			
			return ucObtenido.getSaldoEnCuenta();
		}else {
			return -1d;
		}
		
		
	}
	
	public static String formatearSaldo(Double saldo) {
		DecimalFormat df = new DecimalFormat("#.##");
	    String saldoFormateado = df.format(saldo);
		return saldoFormateado;
	}
	
	public static String uploadImg(File file) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = 
					new HttpPost("https://api.imgbb.com/1/upload?expiration=600&key=ae8f766ed11c5f4b898eea7ded225e28");
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			
			HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
			httpPost.setEntity(postParams);
			
			MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
			mpEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			
			ContentBody cbFile = new FileBody(file, ContentType.IMAGE_PNG);
			mpEntity.addPart("image", cbFile);
			HttpEntity entity = mpEntity.build();
			httpPost.setEntity(entity);
			
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			
			// print result
			String json = response.toString();
			
			ObjectMapper mapper = new ObjectMapper();
			ObjectImg img = mapper.readValue(json, ObjectImg.class);
			httpClient.close();
			return img.getData().getUrl();
		} catch (IOException e) {
			return null;
		}
		
	}

}
