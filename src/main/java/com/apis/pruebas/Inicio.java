package com.apis.pruebas;

import javax.swing.*;
import java.io.IOException;

public class Inicio {
public static void main(String args[]) throws IOException {


    int opcion_menu=-1;
    String botones[]={"1.ver gatos","2.Ver Favoritos","3.salir"};
    do {
        String opcion= (String) JOptionPane.showInputDialog(null,
                "Cat for java","Menu principal",JOptionPane.INFORMATION_MESSAGE,
                null,botones,botones[0]);
        for (int i=0;i<botones.length;i++){
            if(opcion.equals(botones[i])){
                opcion_menu=i;
            }
        }
        switch (opcion_menu){
            case 0:
                CatService.seeCats();
                break;
            case 1:
                Cat cats = new Cat();
                CatService.seeFavorites(cats.getApiKey());
            case 3:
                break;
            default:;
        }
    }while (opcion_menu!=1);

    }

}
