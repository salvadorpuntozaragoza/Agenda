import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Agenda extends JFrame {

	private int index, state = 0;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtEdad;
	private JTextField txtTelefono;
	private JTextField txtCorreo;
	private DefaultListModel<Persona> modelo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Agenda frame = new Agenda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public Agenda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel pnlDatos = new JPanel();
		contentPane.add(pnlDatos);
		pnlDatos.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlCentro = new JPanel();
		pnlDatos.add(pnlCentro, BorderLayout.CENTER);
		pnlCentro.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblNombre = new JLabel("Nombre");
		pnlCentro.add(lblNombre);
		
		txtNombre = new JTextField();
		pnlCentro.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblEdad = new JLabel("Edad");
		pnlCentro.add(lblEdad);
		
		txtEdad = new JTextField();
		pnlCentro.add(txtEdad);
		txtEdad.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		pnlCentro.add(lblTelefono);
		
		txtTelefono = new JTextField();
		pnlCentro.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblCorreo = new JLabel("Correo");
		pnlCentro.add(lblCorreo);
		
		txtCorreo = new JTextField();
		pnlCentro.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Genero");
		pnlCentro.add(lblNewLabel);
		
		JPanel pnlGenero = new JPanel();
		pnlCentro.add(pnlGenero);
		
		ButtonGroup grupo = new ButtonGroup();
		
		
		JRadioButton rdbtnHombre = new JRadioButton("Hombre");
		pnlGenero.add(rdbtnHombre);
		
		
		JRadioButton rdbtnMujer = new JRadioButton("Mujer");
		pnlGenero.add(rdbtnMujer);
		
		grupo.add(rdbtnHombre);
		grupo.add(rdbtnMujer);
		
		JPanel pnlBotones = new JPanel();
		pnlDatos.add(pnlBotones, BorderLayout.SOUTH);
		
		JButton btnNuevo = new JButton("Nuevo");
		pnlBotones.add(btnNuevo);
		
		JButton btnAgregar = new JButton("Agregar");
		pnlBotones.add(btnAgregar);
		
		JButton btnEditar = new JButton("Editar");
		pnlBotones.add(btnEditar);
		
		JButton btnEliminar = new JButton("Eliminar");
		pnlBotones.add(btnEliminar);
		
		JPanel pnlContactos = new JPanel();
		contentPane.add(pnlContactos);
		pnlContactos.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnlContactos.add(scrollPane);
		
		final JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				index = list.getSelectedIndex();
				//Persona p = modelo.get(index);
				//txtNombre.setText(p.getNombre());
			}
		});
		scrollPane.setViewportView(list);
		
		
		modelo = new DefaultListModel<Persona>();
		
		try {
			FileInputStream file = new FileInputStream("archivos/text.txt");
			ObjectInputStream ois = new ObjectInputStream(file);
			modelo = (DefaultListModel<Persona>) ois.readObject();
			file.close();
			ois.close();
		}catch(Exception e2){
			e2.printStackTrace();
		}
		
		list.setModel(modelo);
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText();
				String eda = txtEdad.getText();
				int edad = Integer.parseInt(txtEdad.getText());
				Genero genero = rdbtnHombre.isSelected()?Genero.HOMBRE:Genero.MUJER;
				String telefono = txtTelefono.getText();
				String correo = txtCorreo.getText();
				
				if(nombre.isEmpty()) 
					JOptionPane.showMessageDialog(contentPane, "Error tiene que añadir un nombre");
				if(eda.isEmpty())
					JOptionPane.showMessageDialog(contentPane, "Error tiene que añadir una edad");
				else{
					
				Persona p = new Persona(nombre, edad, genero, telefono, correo);
				modelo.addElement(p);
				
				try {
					FileOutputStream file = new FileOutputStream("archivos/text.txt");
					ObjectOutputStream oos = new ObjectOutputStream(file);
					oos.writeObject(modelo);
					file.close();
					oos.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
		});
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.remove(index);
				
				
				try {
					FileOutputStream file = new FileOutputStream("archivos/text.txt");
					ObjectOutputStream oos = new ObjectOutputStream(file);
					oos.writeObject(modelo);
					file.close();
					oos.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(state == 0){
					JOptionPane.showMessageDialog(contentPane, "Introduce los nuevos datos en los paneles. Cuando termines vuelve a presionar el boton editar ");
					state = 1;
				}else{
					modelo.get(index).setNombre(txtNombre.getText());
					modelo.get(index).setEdad(Integer.parseInt(txtEdad.getText()));
					modelo.get(index).setGenero(rdbtnHombre.isSelected()?Genero.HOMBRE:Genero.MUJER);
					modelo.get(index).setTelefono(txtTelefono.getText());
					modelo.get(index).setCorreo(txtCorreo.getText());
					state = 0;
					
					try {
						FileOutputStream file = new FileOutputStream("archivos/text.txt");
						ObjectOutputStream oos = new ObjectOutputStream(file);
						oos.writeObject(modelo);
						file.close();
						oos.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					list.setSelectedIndex(list.getLastVisibleIndex());
				}
			}
		});
		
		
		
		
	}

}
