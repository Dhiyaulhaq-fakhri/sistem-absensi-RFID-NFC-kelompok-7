/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package serial;


/**
 *
 * @author Lenovo
 */
public interface SerialDataHandler<T> {
    void onDataReceived(T data);
}
