import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Date;

import javax.swing.*;
public class Principal extends JFrame  implements Runnable{
	JTextArea area=new JTextArea();
	JTextField field=new JTextField(15);
	JButton boton=new JButton();
	String fieldserver="a abandonado la conversacion ";
	private Container contenedor; 
	private Font font = new Font("serief", Font.ITALIC, 18); 
	JScrollPane scroll = new JScrollPane();
	String entrada;
    OutputStream osalida;
	DataOutputStream dsalida;
	boolean salida=true;
	InputStream ientrada;
	DataInputStream dentrada;
	Socket cliente;
	Thread hiloarea;
	boolean abierto = false;
	public Principal() {
		contenedor = this.getContentPane(); 
		contenedor.setLayout(new BorderLayout());
		setSize(800,565);
		setLocation(100,90);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){System.exit(0);}});
		servidor();
		setVisible(true);	
		scroll.setViewportView(area);
		try {	
			cliente = new Socket("127.0.0.1", 3000);  
			osalida = cliente.getOutputStream();
			dsalida = new DataOutputStream(osalida);
			ientrada = cliente.getInputStream();
			dentrada = new DataInputStream(ientrada);
			entrada = dentrada.readUTF();
	        area.setText(area.getText()+entrada);
		}
		catch (Exception e) {}
		hiloarea=new Thread(this);
		hiloarea.start();	
	}
	public void servidor() { 
		try {} catch (Exception e) {}  
		this.setLayout(null);
		JPanel Pservidor=new JPanel();
		Pservidor.setLayout(new BorderLayout());
		JPanel Pcliente=new JPanel();
		Pcliente.setLayout(new BorderLayout());
		JPanel pcentro=new JPanel();
		pcentro.setLayout(new FlowLayout());
		this.field.setText("");
		this.boton.setText("ENVIAR");
		pcentro.setBackground(Color.BLUE);
		pcentro.add(new JLabel("INGRESAR TEXTO"));
		pcentro.add(this.field);
		pcentro.add(this.boton);
		boton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent evento){
		try {dsalida.writeUTF(field.getText()+"\n");} 
		catch (IOException e) {e.printStackTrace();}
		}});
		addWindowListener(new java.awt.event.WindowAdapter() {public void windowActivated(java.awt.event.WindowEvent evt) {
            	try {if(abierto)dsalida.writeUTF("vistos"+"\n");abierto=true;} 
            	catch (IOException e) {	e.printStackTrace();}
            }});
		Date fecha = new Date();
		Pcliente.add(pcentro, BorderLayout.CENTER);
		
		Pservidor.add(new JLabel("Hora inicio sesion : 2015/"+fecha.getMonth()+"/"+fecha.getDay()+"  "+fecha.getHours()+":"+fecha.getMinutes()), BorderLayout.SOUTH);
		
		Pservidor.add(scroll, BorderLayout.CENTER);
		Pservidor.add(Pcliente, BorderLayout.NORTH);
		
	
		JLabel logo1=new JLabel(new ImageIcon("logo1.jpg"));
		logo1.setBounds(0, 0, 1000, 1000);
		Pservidor.setBounds(0, 0, 800, 565);
		//this.add(logo1);
		this.add(Pservidor);
		
	}
public void cerrarsesion(){
		try {
			dsalida.close();
			dentrada.close();
		    cliente.close();
		} catch (IOException e) {e.printStackTrace();}	
	}
public void run() {
	Thread ct= Thread.currentThread();
	if(ct==hiloarea){
		try {do{entrada = dentrada.readUTF();area.setText(area.getText()+entrada);}while(true);}
		catch (Exception e) {}
	}}		

	public static void main(String[] args) {Principal obj= new Principal();}}
