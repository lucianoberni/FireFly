package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import interfaces.IComportamiento;

public class Luciernaga implements IComportamiento{

	public String id;
	public HashMap<Character, Integer> elementos;
	public HashMap<Character, Integer> auxiliar;
	public int iteracion;
	public Luciernaga(){}
	
	public Luciernaga(String unId, HashMap<Character, Integer> unElemento){
		this.id = unId;
		this.elementos = unElemento;
		this.auxiliar = new HashMap<Character,Integer>();
		this.iteracion = 0;
	}
	
	public int getIteracion() {
		return iteracion;
	}

	public void setIteracion(int iteracion) {
		this.iteracion = this.iteracion + iteracion;
	}

	public void setId(String unId){
		this.id = unId;
	}
	
	public String getId(){
		return id;
	}

	public void setElementos(HashMap<Character, Integer> unElemento){
		this.elementos = unElemento;
	}
	
	public HashMap<Character, Integer> getElementos(){
		return elementos;
	}
	
	
	@Override
	public int intensidad(char[] operador1, char[] operador2, char[] resultado) {
		int intensidad = 0;
		int peso = 1;
		for (int i=0; i < operador1.length; i++){
			intensidad = intensidad + ((elementos.get(operador1[i]) + elementos.get(operador2[i])-elementos.get(resultado[i]))*peso);
			peso = peso*10;
		}
		if(operador1.length < resultado.length){
			intensidad = intensidad - (resultado[resultado.length-1]*peso);
		}
		return intensidad;
	}

	@Override
	public boolean atractivo(Luciernaga unaLuciernaga, char[] operador1, char[] operador2, char[] resultado) {
		if (Math.abs(this.intensidad(operador1, operador2, resultado)) <= Math.abs(unaLuciernaga.intensidad(operador1, operador2, resultado))){
			return true; //movimiento aleatorio
		}else{
			return false;//movimiento hacia la luciernaga que comparo
		}
	}

	@Override
	public void desplazamiento(Luciernaga unaLuciernaga) {
		Random valor = new Random();
		int unNumero = valor.nextInt(10);
		while(!this.elementos.containsValue(unNumero)){
			unNumero = valor.nextInt(10);
		}
		
		boolean bandera = true;
		while (bandera){
			char caracter = obtenerCaracter(this.elementos, unNumero);
			if(caracter != '-'){
				permutar(unaLuciernaga.elementos, caracter, unNumero);
				unNumero = unaLuciernaga.elementos.get(caracter);
			}else{
				bandera = false;
			}
		}
		
		if(!auxiliar.isEmpty()){
			Iterator it = auxiliar.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry e = (Map.Entry)it.next();
				elementos.put((char)e.getKey(), (int)e.getValue());
			}
		}

		Iterator it2 = elementos.entrySet().iterator();
		while(it2.hasNext()){
			Map.Entry e1 = (Map.Entry)it2.next();
			//System.out.println(e1.getKey() + " - " + e1.getValue());
		}
	}

	private char obtenerCaracter(HashMap<Character,Integer> unDiccionario, int unValor){
		Iterator it = unDiccionario.entrySet().iterator();
		char unaLetra = '-';
		while(it.hasNext()){
			Map.Entry e = (Map.Entry)it.next();
			if(unValor == (int)e.getValue()){
				unaLetra =  (char)e.getKey();
				break;
			}
		}
		return unaLetra;
	}
	
//Alfa-Step
	public void alfaStep(){
		Random valor = new Random();
		boolean bandera = true;
		int unValor = 9999;
		//Encontrar un numero aleatorio que exista
		while(bandera){
			unValor = valor.nextInt(10);
			if(elementos.containsValue(unValor)){
				bandera = false;
			}
		}
		//Encontrar la letra de ese numero aleatorio que existe
		char unCar = obtenerCaracter(this.elementos, unValor);
		boolean bandera2 = true;
		//Encontrar un vlaor que no exista para la letra 
		while (bandera2){
			unValor = valor.nextInt(10);
			if(!elementos.containsValue(unValor)){
				bandera2 = false;
			}
		}
		
	}
		
	private void permutar(HashMap<Character,Integer> mejor, char unChar, int unInt){
		insertar(unChar, (int)mejor.get(unChar));
		eliminar(unChar, elementos.get(unChar));
	}
	
	private void eliminar(char unChar, int unInt){
		elementos.remove(unChar, unInt);
	}
	
	private void insertar(char unChar, int unInt){
		auxiliar.put(unChar, unInt);
	}
	
	public String toString(){
		String resultado = " ";
		Iterator it = elementos.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry e = (Map.Entry)it.next();
			resultado = resultado + e.getKey() + ":" + e.getValue() + " ";
		}
		return resultado;
	}
	
}
