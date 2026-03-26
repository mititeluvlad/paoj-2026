package com.pao.laboratory05.audit;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService{
    private Angajat[] angajati = new Angajat[0];
    private AuditEntry[] auditLog = new AuditEntry[0];
    
    private AngajatService(){}

    private static class Holder{
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance(){
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a){
        int n = angajati.length;
        Angajat[] ang = new Angajat[n+1];
        System.arraycopy(angajati, 0, ang, 0, n);
        ang[n] = a;
        angajati = ang;
        logAction("ADD", a.getNume());
    }

    public void printAll(){
        int n = angajati.length;
        for(int i = 0; i < n; ++i){
            System.out.println(angajati[i].toString());
        }
    }

    public void listBySalary(){
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);
        int n = angajati.length;
        for(int i = 0; i < n; ++i){
            System.out.println(copy[i].toString());
        }
    }

    public void findByDepartament(String numeDept){
        logAction("FIND_BY_DEPT", numeDept);
        int ok = 0;
        int n = angajati.length;
        for(int i = 0; i < n; ++i){
            if(angajati[i].getDepartament().nume().equalsIgnoreCase(numeDept)){
                ok = 1;
                System.out.println(angajati[i].toString());
            }
        }
        if(ok == 0){
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }

    private void logAction(String action, String target){
        int n = auditLog.length;
        AuditEntry[] a = new AuditEntry[n+1];
        System.arraycopy(auditLog, 0, a, 0,n);
        AuditEntry ad = new AuditEntry(action, target,LocalDateTime.now().toString());
        a[n] = ad;
        auditLog = a;
    }

    public void printAuditLog(){
        for(int i = 0; i < auditLog.length; ++i){
            System.out.println(auditLog[i].toString());
        }
    }
}