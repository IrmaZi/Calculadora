/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Irma Zi Canul
 */
public class Calculadora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Escribe la expresión: ");
        Scanner entrada = new Scanner(System.in);

        //se depura la expresion 
        String expr = depurar(entrada.nextLine());
        String[] arrayInfix = expr.split(" ");

        Stack< String> E = new Stack< String>();//Pila entrada
        Stack< String> O = new Stack< String>();//Pila temporal para operadores
        Stack< String> S = new Stack< String>();//Pila salida

        for (int i = arrayInfix.length - 1; i >= 0; i--) {
            E.push(arrayInfix[i]);
        }

        try {
            //Conversión de Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (pref(E.peek())) {
                    case 1:
                        O.push(E.pop());
                        break;
                    case 3:
                    case 4:
                        while (pref(O.peek()) >= pref(E.peek())) {
                            S.push(O.pop());
                        }
                        O.push(E.pop());
                        break;
                    case 2:
                        while (!O.peek().equals("(")) {
                            S.push(O.pop());
                        }
                        O.pop();
                        E.pop();
                        break;
                    default:
                        S.push(E.pop());
                }
            }

            //Eliminacion 
            String infix = expr.replace(" ", "");
            String postfix = S.toString().replaceAll("[\\]\\[,]", "");

            System.out.println("Infija: " + infix);
            System.out.println("Postfija: " + postfix);

        } catch (Exception ex) {
            System.out.println("Expresión invalida");
            System.err.println(ex);
        }
    }

    private static String depurar(String s) {
        s = s.replaceAll("\\s+", "");
        s = "(" + s + ")";
        String simbols = "+-*/()";
        String str = "";

        //espacios 
        for (int i = 0; i < s.length(); i++) {
            if (simbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            } else {
                str += s.charAt(i);
            }
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    private static int pref(String op) {
        int prf = 99;
        if (op.equals("^")) {
            prf = 5;
        }
        if (op.equals("*") || op.equals("/")) {
            prf = 4;
        }
        if (op.equals("+") || op.equals("-")) {
            prf = 3;
        }
        if (op.equals(")")) {
            prf = 2;
        }
        if (op.equals("(")) {
            prf = 1;
        }
        return prf;
    }
}
