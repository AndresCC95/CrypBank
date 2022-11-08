package com.example.crypbank;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Usuario {

    private DatabaseReference databaseReference;

    private String Nombre;
    private String Apellidos;
    private String Dni;
    private String Email;
    private String Clave;
    private double Saldo;

    public Usuario(String nombre, String apellidos, String dni, String email, String clave, double saldo) {
        Nombre = nombre;
        Apellidos = apellidos;
        Dni = dni;
        Email = email;
        Clave = clave;
        Saldo = saldo;
    }

    public Usuario() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference= db.getReference(Usuario.class.getSimpleName());
    }

    public Task<Void> add(Usuario usu) {
        return databaseReference.push().setValue(usu);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    //*******GETTER && SETTER*******//

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String dni) {
        Dni = dni;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }
}
