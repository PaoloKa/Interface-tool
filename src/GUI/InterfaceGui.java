package GUI;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import com.interfaces.ComponentConstants;
import com.interfaces.FontDecoding;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ComponentDefinition;

import properties.PropertyValues;
import sprite.ImageUtils;
import sprite.Sprite;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;


public class InterfaceGui  extends JFrame {

	private static final long serialVersionUID = 1L;
    private JTextField txt_type;
    private JTextField txt_hash;
	private JTabbedPane tabbedPane;
    private JTextField txt_x;
    private JTextField txt_y;
    private JTextField txt_height;
    private JTextField txt_widht;
    private JTextField txt_text;
    private JTextField txt_leftclick;
    private JTextField txt_parent;
    private JTextField txt_option1;
	private JTextField txt_option2;
    private JTextField txt_option3;
    private JTextField txt_option4;
	private JTextField txt_option5;
    private JTextField txt_modex;
    private JTextField txt_positionmodeY;
    private JCheckBox chckbxShowHiddenComps;
	private JCheckBox chckbxShowRectangles;
    private JTextField txt_border;
	private JMenuItem mntmPackSprite;
    private JTextField txt_sprite;
    private JTextField txt_modeHeight;
	private JTextField txt_widthMode;
    private JTextField txt_model;
	private JCheckBox chckbxHidden;
    private JTextField txt_popup;
    private JTextField txt_fullonhover;
    private JTextField txt_mouseLeave;
    private JTextField txt_onload;
    private JTextField txt_anObjectArray4771;
    private JTextField txt_anObjectArray4768;
    private JTextField txt_anObjectArray4807;
    private JTextField txt_anObjectArray4742;
    private JTextField txt_anObjectArray4788;
    private JTextField txt_anObjectArray4701;
    private JTextField txt_configs;
    private JTextField txt_anObjectArray4770;
    private JTextField txt_font;
    private JTextField txt_animationId;
    private JTextField txt_color;
    private JTextField txt_trans;
    private JTextField txt_multi;
	private JTextField txt_qcopy_inter;
	private JTextField txt_qcopy_comp;
	private JTextField txt_scrollX;
	private JTextField txt_scrollY;
    private JTextField txt_anIntArray4833;
    private JTextField txt_anIntArray4789;
    private JTextField txt_anIntArray4829;
    private JTextField txt_anIntArray4805;
    private JTextField txt_anObjectArray4774;
    private JTextField txt_anObjectArray4803;
    private JTextField txt_anObjectArray4680;
    private JTextField txt_anObjectArray4856;
    private JTextField txt_anObjectArray4852;
    private JTextField txt_anObjectArray4711;
    private JTextField txt_anObjectArray4753;
	private JTextField txt_anObjectArray4688;
    private JTextField txt_anObjectArray4775;
    private JTextField txt_xali;
	private JTextField txt_yali;
    protected JList interface_list;
	private JTextField txt_interId;
	protected JPanel panel;
	protected JScrollPane scrollPane_2 ;
	protected JScrollPane scrollPane_1;
	protected JComboBox comboBox ;
	protected JCheckBox chckbxShowContainers;
	protected JCheckBox chckbxRepeat;
	protected JCheckBox chckbxHorizontalFlip ;
	protected JCheckBox chckbxVerticalFlip;
	protected JCheckBox chckbxRefreshTreeOn;
	protected JCheckBox chckbxFilled;
	private JCheckBox chckbxRealFonttesting;
	public static JProgressBar progressBar;
	public ProgressMonitor progressMonitor;
	private JTextArea textArea = new JTextArea(15, 30);
    private  BufferedImage  result;

	/**
	 * icomp shit
	 */
	private int currentInterface=-1;
	private int selectedComp=-1;
	private ComponentDefinition copiedComp = null;
	/**
	 * launches the app
	 * @param args
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try
					{
						UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
						JFrame.setDefaultLookAndFeelDecorated(true);
						JDialog.setDefaultLookAndFeelDecorated(true);

					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null,
								""+e);
						System.out.println(e);
					}
					try {
						PropertyValues.loadValues();
					} catch(Exception e){
						JOptionPane.showMessageDialog(null,
								"Properties can not be found, make sure you've a config.properties file.");
					}
					Cache.init();
					InterfaceGui frame = new InterfaceGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * GUI constructor
	 */
	public InterfaceGui() {
		setTitle("Interface editor");
        TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "Console");
        //System.setOut(new PrintStream(taOutputStream));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1035, 743);
		getContentPane().setLayout(null);

		JLabel lblQuickCopy = new JLabel("Quick copy");
		lblQuickCopy.setBounds(749, 449, 86, 16);
		getContentPane().add(lblQuickCopy);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 54, 193, 331);
		getContentPane().add(scrollPane);
		interface_list = new JList(populateList());
		scrollPane.setViewportView(interface_list);
		interface_list.addListSelectionListener(evt -> {
            if (evt.getValueIsAdjusting())
                return;
            int id  = Integer.parseInt(interface_list.getSelectedValue().toString().replaceAll("Interface: ", ""));
            currentInterface = id;
            drawTree(id);
            cleanValues();
        });

		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem export = new JMenuItem("Export");
		popupMenu.add(export);
		interface_list.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				if (SwingUtilities.isRightMouseButton(me)    // if right mouse button clicked
						&& !interface_list.isSelectionEmpty()            // and list selection is not empty
						&& interface_list.locationToIndex(me.getPoint()) // and clicked point is
						== interface_list.getSelectedIndex()) {       //   inside selected item bounds
					popupMenu.show(interface_list, me.getX(), me.getY());
				}
			}
		});
		export.addActionListener(arg0 -> {
            try {
                exportInterface(currentInterface);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(scrollPane_2, "File could not be dumped, error: "+e.getMessage());
            }
        });
		/*
		 * input field for finding a interface
		 */
		txt_interId = new JTextField();
		txt_interId.setBounds(10, 11, 68, 32);
		getContentPane().add(txt_interId);
		txt_interId.setColumns(10);

		/**
		 * the button for looking up
		 */
		JButton btnFind = new JButton("find");
		btnFind.setBounds(88, 11, 115, 32);
		btnFind.addActionListener(new ActionListener() {
			//click action
			public void actionPerformed(ActionEvent arg0) {

				int id = Integer.parseInt(txt_interId.getText());
				currentInterface = id;
				drawTree(id);
			}
		});
		getContentPane().add(btnFind);

		/**
		 * scrollpane for the jtree
		 */
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(749, 54, 229, 339);
		getContentPane().add(scrollPane_1);
		//jtree itself
		JTree componentTree = new JTree(createInterfaceTree(1));
		scrollPane_1.setViewportView(componentTree);
		componentTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode =  (DefaultMutableTreeNode)componentTree.getLastSelectedPathComponent();

				int id = Integer.parseInt(selectedNode.getUserObject().toString().replaceAll("Component ", ""));
				selectedComp = id;
				setValues(currentInterface,id);
			}
		});
		/**
		 * interface panel
		 */
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(225, 54, 520, 339);
		getContentPane().add(scrollPane_2);
		/**
		 * same
		 */
		panel = new JPanel();
		panel.setBounds(227, 54, 518, 339);
		getContentPane().add(panel);
		/**
		 * component buttons
		 */
        JButton btnCopy = new JButton("copy");
		btnCopy.setToolTipText("Copy selected interface");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copiedComp = ComponentDefinition.getInterfaceComponent(currentInterface, selectedComp);
			}
		});
		btnCopy.setBounds(904, 404, 78, 32);
		getContentPane().add(btnCopy);

        JButton btnDelete = new JButton("delete");
		btnDelete.setToolTipText("Deletes the selected component");
		btnDelete.setBounds(728, 404, 78, 32);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(selectedComp <= 0) {
					JOptionPane.showMessageDialog(scrollPane_2, "Please select an component first.");
				} else {
					ComponentDefinition c =  ComponentDefinition.getInterfaceComponent(currentInterface, selectedComp);
					String message =  (c.type == ComponentConstants.CONTAINER ? "Are you sure that you want to remove component "+selectedComp+" from interface "+currentInterface+" ? NOTE: this component is a container, childs will be removed aswell." : "Are you sure that you want to remove  component "+selectedComp+" from interface "+currentInterface+" ?");
					int option = JOptionPane.showConfirmDialog( componentTree,
							message,
							"Inane warning",
							JOptionPane.YES_NO_OPTION);
					if(option == 0) {
						try{
							Cache.STORE.getIndexes()[3].removeFile(currentInterface,selectedComp);
							Cache.STORE.getIndexes()[3].resetCachedFiles();
							Cache.STORE.getIndexes()[3].rewriteTable();
							try {
								Cache.init(); // otherwise it doesn't work xd
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} finally {
							drawTree(currentInterface);
						}
					}
				}
			}
		});
		getContentPane().add(btnDelete);

        JButton btnNewButton = new JButton("paste");
		btnNewButton.setToolTipText("Past your copied component into the selected interface");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentInterface == -1){
					return;
				}
				pasteComponent();
			}
		});
		btnNewButton.setBounds(816, 404, 78, 32);
		getContentPane().add(btnNewButton);
		/**
		 * tabbed pane
		 */
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(225, 394, 493, 276);
		getContentPane().add(tabbedPane);
		/**
		 * tab 2
		 */
		/**
		 * tab 3
		 */

        JPanel generalTab = new JPanel();
		tabbedPane.addTab("General", null, generalTab, null);
		generalTab.setLayout(null);

        JTextField txtType = new JTextField();
		txtType.setBounds(10, 11, 67, 20);
		generalTab.add(txtType);
		txtType.setEditable(false);
		txtType.setText("Type");
		txtType.setColumns(10);

		txt_type = new JTextField();
		txt_type.setEnabled(false);
		txt_type.setBounds(74, 11, 108, 20);
		generalTab.add(txt_type);
		txt_type.setColumns(10);

        JTextField txtHash = new JTextField();
		txtHash.setBounds(10, 31, 86, 20);
		generalTab.add(txtHash);
		txtHash.setEditable(false);
		txtHash.setText("Hash");
		txtHash.setColumns(10);

		txt_hash = new JTextField();
		txt_hash.setBounds(96, 31, 86, 20);
		generalTab.add(txt_hash);
		txt_hash.setColumns(10);

		txt_x = new JTextField();
		txt_x.setBounds(96, 49, 86, 20);
		generalTab.add(txt_x);
		txt_x.setColumns(10);

        JTextField txtXPosition = new JTextField();
		txtXPosition.setEditable(false);
		txtXPosition.setText("X position");
		txtXPosition.setBounds(10, 49, 86, 20);
		//txtXPosition
		generalTab.add(txtXPosition);
		txtXPosition.setColumns(10);

		txt_y = new JTextField();
		txt_y.setBounds(96, 68, 86, 20);
		generalTab.add(txt_y);
		txt_y.setColumns(10);

        JTextField txtYPosition = new JTextField();
		txtYPosition.setEditable(false);
		txtYPosition.setText("Y position");
		txtYPosition.setBounds(10, 68, 86, 20);
		generalTab.add(txtYPosition);
		txtYPosition.setColumns(10);

		txt_height = new JTextField();
		txt_height.setBounds(96, 86, 86, 20);
		generalTab.add(txt_height);
		txt_height.setColumns(10);

        JTextField txtHeight = new JTextField();
		txtHeight.setEditable(false);
		txtHeight.setText("Height");
		txtHeight.setBounds(10, 86, 86, 20);
		generalTab.add(txtHeight);
		txtHeight.setColumns(10);

        JTextField txtWidht = new JTextField();
		txtWidht.setEditable(false);
		txtWidht.setText("Widht");
		txtWidht.setBounds(10, 106, 86, 20);
		generalTab.add(txtWidht);
		txtWidht.setColumns(10);

		txt_widht = new JTextField();
		txt_widht.setBounds(96, 106, 86, 20);
		generalTab.add(txt_widht);
		txt_widht.setColumns(10);

		chckbxHidden = new JCheckBox("Hidden");
		chckbxHidden.setBounds(224, 114, 97, 23);
		generalTab.add(chckbxHidden);

        JTextField txtParent = new JTextField();
		txtParent.setToolTipText("Parent id is the hash id of another component");
		txtParent.setEditable(false);
		txtParent.setText("Parent");
		txtParent.setBounds(198, 11, 86, 20);
		generalTab.add(txtParent);
		txtParent.setColumns(10);

		txt_parent = new JTextField();
		txt_parent.setBounds(283, 11, 86, 20);
		generalTab.add(txt_parent);
		txt_parent.setColumns(10);

        JTextField text_positionmodeX = new JTextField();
		text_positionmodeX.setText("Position mode x");
		text_positionmodeX.setEditable(false);
		text_positionmodeX.setBounds(198, 31, 123, 20);
		generalTab.add(text_positionmodeX);
		text_positionmodeX.setColumns(10);

		txt_modex = new JTextField();
		txt_modex.setBounds(318, 31, 51, 20);
		generalTab.add(txt_modex);
		txt_modex.setColumns(10);

        JTextField text_modeY = new JTextField();
		text_modeY.setText("Position mode y");
		text_modeY.setEditable(false);
		text_modeY.setBounds(198, 49, 123, 20);
		generalTab.add(text_modeY);
		text_modeY.setColumns(10);

		txt_positionmodeY = new JTextField();
		txt_positionmodeY.setBounds(318, 49, 51, 20);
		generalTab.add(txt_positionmodeY);
		txt_positionmodeY.setColumns(10);

        JTextField txtHeightMode = new JTextField();
		txtHeightMode.setText("Height mode");
		txtHeightMode.setEditable(false);
		txtHeightMode.setColumns(10);
		txtHeightMode.setBounds(198, 67, 123, 20);
		generalTab.add(txtHeightMode);

        JTextField txtWidhtMode = new JTextField();
		txtWidhtMode.setText("Widht mode");
		txtWidhtMode.setEditable(false);
		txtWidhtMode.setColumns(10);
		txtWidhtMode.setBounds(198, 87, 123, 20);
		generalTab.add(txtWidhtMode);

		txt_modeHeight = new JTextField();
		txt_modeHeight.setColumns(10);
		txt_modeHeight.setBounds(318, 67, 51, 20);
		generalTab.add(txt_modeHeight);

		txt_widthMode = new JTextField();
		txt_widthMode.setColumns(10);
		txt_widthMode.setBounds(318, 87, 51, 20);
		generalTab.add(txt_widthMode);

        JTextField txtColor = new JTextField();
		txtColor.setText("Color");
		txtColor.setEditable(false);
		txtColor.setColumns(10);
		txtColor.setBounds(11, 127, 86, 20);
		generalTab.add(txtColor);

		txt_color = new JTextField();
		txt_color.setColumns(10);
		txt_color.setBounds(96, 127, 86, 20);
		generalTab.add(txt_color);

        JTextField txtTransparency = new JTextField();
		txtTransparency.setEditable(false);
		txtTransparency.setText("Transparency");
		txtTransparency.setBounds(10, 146, 101, 22);
		generalTab.add(txtTransparency);
		txtTransparency.setColumns(10);

		txt_trans = new JTextField();
		txt_trans.setBounds(106, 146, 76, 22);
		generalTab.add(txt_trans);
		txt_trans.setColumns(10);

		txt_scrollX = new JTextField();
		txt_scrollX.setBounds(116, 188, 116, 22);
		generalTab.add(txt_scrollX);
		txt_scrollX.setColumns(10);

		txt_scrollY = new JTextField();
		txt_scrollY.setBounds(116, 211, 116, 22);
		generalTab.add(txt_scrollY);
		txt_scrollY.setColumns(10);

        JLabel lblContainerScrollX = new JLabel("Container scroll X");
		lblContainerScrollX.setBounds(10, 192, 86, 14);
		generalTab.add(lblContainerScrollX);

        JLabel lblContainerScrollY = new JLabel("Container scroll Y");
		lblContainerScrollY.setBounds(11, 215, 85, 14);
		generalTab.add(lblContainerScrollY);
        JPanel textTab = new JPanel();
		tabbedPane.addTab("Text", null, textTab, null);
		textTab.setLayout(null);

        JTextField txtText = new JTextField();
		txtText.setText("Text");
		txtText.setEditable(false);
		txtText.setBounds(10, 11, 86, 20);
		textTab.add(txtText);
		txtText.setColumns(10);

		txt_text = new JTextField();
		txt_text.setBounds(95, 11, 86, 20);
		textTab.add(txt_text);
		txt_text.setColumns(10);

        JTextField txtLeftClick = new JTextField();
		txtLeftClick.setEditable(false);
		txtLeftClick.setText("Left click");
		txtLeftClick.setBounds(10, 29, 86, 20);
		textTab.add(txtLeftClick);
		txtLeftClick.setColumns(10);

		txt_leftclick = new JTextField();
		txt_leftclick.setBounds(95, 29, 86, 20);
		textTab.add(txt_leftclick);
		txt_leftclick.setColumns(10);

        JTextField txtRightclick = new JTextField();
		txtRightclick.setEditable(false);
		txtRightclick.setText("Rightclick 1");
		txtRightclick.setBounds(206, 11, 86, 20);
		textTab.add(txtRightclick);
		txtRightclick.setColumns(10);

		txt_option1 = new JTextField();
		txt_option1.setBounds(292, 11, 86, 20);
		textTab.add(txt_option1);
		txt_option1.setColumns(10);

		txt_option2 = new JTextField();
		txt_option2.setBounds(292, 29, 86, 20);
		textTab.add(txt_option2);
		txt_option2.setColumns(10);

        JTextField txtRightclick_1 = new JTextField();
		txtRightclick_1.setEditable(false);
		txtRightclick_1.setText("Rightclick 2");
		txtRightclick_1.setBounds(206, 29, 86, 20);
		textTab.add(txtRightclick_1);
		txtRightclick_1.setColumns(10);

        JTextField txtRightclick_2 = new JTextField();
		txtRightclick_2.setText("Rightclick 3");
		txtRightclick_2.setEditable(false);
		txtRightclick_2.setColumns(10);
		txtRightclick_2.setBounds(206, 50, 86, 20);
		textTab.add(txtRightclick_2);

		txt_option3 = new JTextField();
		txt_option3.setBounds(292, 50, 86, 20);
		textTab.add(txt_option3);
		txt_option3.setColumns(10);

        JTextField txtRightclick_3 = new JTextField();
		txtRightclick_3.setEditable(false);
		txtRightclick_3.setText("Rightclick 4");
		txtRightclick_3.setBounds(206, 69, 86, 20);
		textTab.add(txtRightclick_3);
		txtRightclick_3.setColumns(10);

		txt_option4 = new JTextField();
		txt_option4.setBounds(292, 69, 86, 20);
		textTab.add(txt_option4);
		txt_option4.setColumns(10);

		txt_option5 = new JTextField();
		txt_option5.setBounds(292, 87, 86, 20);
		textTab.add(txt_option5);
		txt_option5.setColumns(10);

        JTextField txtRightclick_4 = new JTextField();
		txtRightclick_4.setText("Rightclick 5");
		txtRightclick_4.setEditable(false);
		txtRightclick_4.setBounds(206, 87, 86, 20);
		textTab.add(txtRightclick_4);
		txtRightclick_4.setColumns(10);

        JTextField txtfond = new JTextField();
		txtfond.setText("Font id");
		txtfond.setEditable(false);
		txtfond.setBounds(10, 50, 86, 20);
		textTab.add(txtfond);
		txtfond.setColumns(10);

		txt_font = new JTextField();
		txt_font.setBounds(95, 50, 86, 20);
		textTab.add(txt_font);
		txt_font.setColumns(10);

        JTextField txtMulti = new JTextField();
		txtMulti.setEditable(false);
		txtMulti.setText("Multi");
		txtMulti.setBounds(10, 68, 86, 22);
		textTab.add(txtMulti);
		txtMulti.setColumns(10);

		txt_multi = new JTextField();
		txt_multi.setBounds(95, 68, 86, 22);
		textTab.add(txt_multi);
		txt_multi.setColumns(10);

		txt_xali = new JTextField();
		txt_xali.setBounds(95, 142, 86, 20);
		textTab.add(txt_xali);
		txt_xali.setColumns(10);

		txt_yali = new JTextField();
		txt_yali.setBounds(95, 162, 86, 20);
		textTab.add(txt_yali);
		txt_yali.setColumns(10);

		JLabel lblXAli = new JLabel("x ali");
		lblXAli.setBounds(39, 145, 46, 14);
		textTab.add(lblXAli);

		JLabel lblYAli = new JLabel("y ali");
		lblYAli.setBounds(39, 165, 46, 14);
		textTab.add(lblYAli);

        JPanel modelTab = new JPanel();
		tabbedPane.addTab("Model", null, modelTab, null);
		modelTab.setLayout(null);

        JTextField textField_4 = new JTextField();
		textField_4.setBounds(10, 11, 86, 20);
		textField_4.setText("Model id");
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		modelTab.add(textField_4);

		txt_model = new JTextField();
		txt_model.setBounds(93, 11, 86, 20);
		txt_model.setColumns(10);
		modelTab.add(txt_model);

        JTextField txtAnimationId = new JTextField();
		txtAnimationId.setEditable(false);
		txtAnimationId.setText("animation id");
		txtAnimationId.setBounds(10, 31, 86, 20);
		modelTab.add(txtAnimationId);
		txtAnimationId.setColumns(10);

		txt_animationId = new JTextField();
		txt_animationId.setBounds(93, 31, 86, 20);
		modelTab.add(txt_animationId);
		txt_animationId.setColumns(10);

        JPanel figureTab = new JPanel();
		tabbedPane.addTab("Rectangle", null, figureTab, null);
		figureTab.setLayout(null);

		chckbxFilled = new JCheckBox("Filled");
		chckbxFilled.setBounds(147, 71, 113, 25);
		figureTab.add(chckbxFilled);
        JPanel spriteTab = new JPanel();
		tabbedPane.addTab("Sprite", null, spriteTab, null);
		spriteTab.setLayout(null);

        JTextField txtTest = new JTextField();
		txtTest.setEditable(false);
		txtTest.setText("Border thickness");
		txtTest.setBounds(137, 11, 140, 20);
		spriteTab.add(txtTest);
		txtTest.setColumns(10);

		txt_border = new JTextField();
		txt_border.setBounds(276, 11, 86, 20);
		spriteTab.add(txt_border);
		txt_border.setColumns(10);

		chckbxRepeat = new JCheckBox("repeat");
		chckbxRepeat.setBounds(10, 10, 97, 23);
		spriteTab.add(chckbxRepeat);

		chckbxHorizontalFlip = new JCheckBox("horizontal flip");
		chckbxHorizontalFlip.setBounds(10, 36, 121, 23);
		spriteTab.add(chckbxHorizontalFlip);

		chckbxVerticalFlip = new JCheckBox("vertical flip");
		chckbxVerticalFlip.setBounds(10, 62, 97, 23);
		spriteTab.add(chckbxVerticalFlip);

        JTextField textField = new JTextField();
		textField.setText("Sprite id");
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(137, 37, 86, 20);
		spriteTab.add(textField);

		txt_sprite = new JTextField();
		txt_sprite.setColumns(10);
		txt_sprite.setBounds(221, 37, 90, 20);
		spriteTab.add(txt_sprite);

        JPanel scriptTab = new JPanel();
		tabbedPane.addTab("Scripts", null, scriptTab, null);
		scriptTab.setLayout(null);

        JTextField txtOnhover = new JTextField();
		txtOnhover.setText("Mouse over");
		txtOnhover.setEditable(false);
		txtOnhover.setBounds(0, 11, 131, 20);
		scriptTab.add(txtOnhover);
		txtOnhover.setColumns(10);

		txt_fullonhover = new JTextField();
		txt_fullonhover.setBounds(127, 11, 351, 20);
		scriptTab.add(txt_fullonhover);
		txt_fullonhover.setColumns(10);

        JTextField txtMouseLeave = new JTextField();
		txtMouseLeave.setEnabled(false);
		txtMouseLeave.setText("Mouse leave");
		txtMouseLeave.setBounds(0, 32, 131, 20);
		scriptTab.add(txtMouseLeave);
		txtMouseLeave.setColumns(10);

		txt_mouseLeave = new JTextField();
		txt_mouseLeave.setBounds(122, 32, 356, 20);
		scriptTab.add(txt_mouseLeave);
		txt_mouseLeave.setColumns(10);

        JTextField txtAnobjectarray = new JTextField();
		txtAnobjectarray.setEnabled(false);
		txtAnobjectarray.setText("onload");
		txtAnobjectarray.setBounds(0, 52, 131, 20);
		scriptTab.add(txtAnobjectarray);
		txtAnobjectarray.setColumns(10);

		txt_onload = new JTextField();
		txt_onload.setBounds(127, 52, 351, 20);
		scriptTab.add(txt_onload);
		txt_onload.setColumns(10);

        JTextField txtAnobjectarray_1 = new JTextField();
		txtAnobjectarray_1.setEnabled(false);
		txtAnobjectarray_1.setText("anObjectArray4771");
		txtAnobjectarray_1.setBounds(0, 74, 131, 20);
		scriptTab.add(txtAnobjectarray_1);
		txtAnobjectarray_1.setColumns(10);

		txt_anObjectArray4771 = new JTextField();
		txt_anObjectArray4771.setBounds(127, 74, 351, 20);
		scriptTab.add(txt_anObjectArray4771);
		txt_anObjectArray4771.setColumns(10);

        JTextField txtAnobjectarray_7 = new JTextField();
		txtAnobjectarray_7.setText("anObjectArray4770");
		txtAnobjectarray_7.setEnabled(false);
		txtAnobjectarray_7.setColumns(10);
		txtAnobjectarray_7.setBounds(0, 92, 131, 20);
		scriptTab.add(txtAnobjectarray_7);

		txt_anObjectArray4770 = new JTextField();
		txt_anObjectArray4770.setColumns(10);
		txt_anObjectArray4770.setBounds(127, 92, 351, 20);
		scriptTab.add(txt_anObjectArray4770);

        JTextField txtPopup = new JTextField();
		txtPopup.setBounds(0, 112, 131, 21);
		scriptTab.add(txtPopup);
		txtPopup.setEditable(false);
		txtPopup.setText("pop-up");
		txtPopup.setColumns(10);

		txt_popup = new JTextField();
		txt_popup.setBounds(130, 112, 348, 20);
		scriptTab.add(txt_popup);
		txt_popup.setColumns(10);

        JTextField txtAnobjectarray_2 = new JTextField();
		txtAnobjectarray_2.setBounds(0, 132, 131, 20);
		scriptTab.add(txtAnobjectarray_2);
		txtAnobjectarray_2.setEnabled(false);
		txtAnobjectarray_2.setText("anObjectArray4768");
		txtAnobjectarray_2.setColumns(10);

		txt_anObjectArray4768 = new JTextField();
		txt_anObjectArray4768.setBounds(127, 132, 351, 20);
		scriptTab.add(txt_anObjectArray4768);
		txt_anObjectArray4768.setColumns(10);

        JTextField txtAnobjectarray_3 = new JTextField();
		txtAnobjectarray_3.setBounds(0, 151, 131, 20);
		scriptTab.add(txtAnobjectarray_3);
		txtAnobjectarray_3.setEnabled(false);
		txtAnobjectarray_3.setText("anObjectArray4807");
		txtAnobjectarray_3.setColumns(10);

		txt_anObjectArray4807 = new JTextField();
		txt_anObjectArray4807.setBounds(130, 152, 348, 20);
		scriptTab.add(txt_anObjectArray4807);
		txt_anObjectArray4807.setColumns(10);

        JTextField txtAnobjectarray_4 = new JTextField();
		txtAnobjectarray_4.setBounds(0, 171, 131, 20);
		scriptTab.add(txtAnobjectarray_4);
		txtAnobjectarray_4.setEnabled(false);
		txtAnobjectarray_4.setText("anObjectArray4742");
		txtAnobjectarray_4.setColumns(10);

		txt_anObjectArray4742 = new JTextField();
		txt_anObjectArray4742.setBounds(130, 171, 348, 20);
		scriptTab.add(txt_anObjectArray4742);
		txt_anObjectArray4742.setColumns(10);

        JTextField txtAnobjectarray_5 = new JTextField();
		txtAnobjectarray_5.setBounds(0, 191, 131, 20);
		scriptTab.add(txtAnobjectarray_5);
		txtAnobjectarray_5.setEditable(false);
		txtAnobjectarray_5.setText("anObjectArray4788");
		txtAnobjectarray_5.setColumns(10);

		txt_anObjectArray4788 = new JTextField();
		txt_anObjectArray4788.setBounds(130, 191, 348, 20);
		scriptTab.add(txt_anObjectArray4788);
		txt_anObjectArray4788.setColumns(10);

        JTextField txtAnobjectarray_6 = new JTextField();
		txtAnobjectarray_6.setBounds(0, 211, 131, 20);
		scriptTab.add(txtAnobjectarray_6);
		txtAnobjectarray_6.setText("anObjectArray4701");
		txtAnobjectarray_6.setEditable(false);
		txtAnobjectarray_6.setColumns(10);

		txt_anObjectArray4701 = new JTextField();
		txt_anObjectArray4701.setBounds(130, 211, 348, 20);
		scriptTab.add(txt_anObjectArray4701);
		txt_anObjectArray4701.setColumns(10);

        JPanel panel_10 = new JPanel();
		tabbedPane.addTab("Configs", null, panel_10, null);
		panel_10.setLayout(null);

        JTextField txtConfigs = new JTextField();
		txtConfigs.setEditable(false);
		txtConfigs.setText("Configs");
		txtConfigs.setBounds(10, 33, 86, 20);
		panel_10.add(txtConfigs);
		txtConfigs.setColumns(10);

		txt_configs = new JTextField();
		txt_configs.setBounds(95, 33, 263, 20);
		panel_10.add(txt_configs);
		txt_configs.setColumns(10);

        JTextField txtAnintarray = new JTextField();
		txtAnintarray.setText("anIntArray4833");
		txtAnintarray.setEditable(false);
		txtAnintarray.setColumns(10);
		txtAnintarray.setBounds(10, 58, 86, 20);
		panel_10.add(txtAnintarray);

		txt_anIntArray4833 = new JTextField();
		txt_anIntArray4833.setColumns(10);
		txt_anIntArray4833.setBounds(95, 58, 263, 20);
		panel_10.add(txt_anIntArray4833);

        JTextField txtAnintarray_1 = new JTextField();
		txtAnintarray_1.setText("anIntArray4789");
		txtAnintarray_1.setEditable(false);
		txtAnintarray_1.setColumns(10);
		txtAnintarray_1.setBounds(10, 84, 86, 20);
		panel_10.add(txtAnintarray_1);

		txt_anIntArray4789 = new JTextField();
		txt_anIntArray4789.setColumns(10);
		txt_anIntArray4789.setBounds(95, 84, 263, 20);
		panel_10.add(txt_anIntArray4789);

        JTextField txtAnintarray_2 = new JTextField();
		txtAnintarray_2.setText("anIntArray4829");
		txtAnintarray_2.setEditable(false);
		txtAnintarray_2.setColumns(10);
		txtAnintarray_2.setBounds(10, 110, 86, 20);
		panel_10.add(txtAnintarray_2);

		txt_anIntArray4829 = new JTextField();
		txt_anIntArray4829.setColumns(10);
		txt_anIntArray4829.setBounds(95, 110, 263, 20);
		panel_10.add(txt_anIntArray4829);

        JTextField txtAnintarray_3 = new JTextField();
		txtAnintarray_3.setText("anIntArray4805");
		txtAnintarray_3.setEditable(false);
		txtAnintarray_3.setColumns(10);
		txtAnintarray_3.setBounds(10, 137, 86, 20);
		panel_10.add(txtAnintarray_3);

		txt_anIntArray4805 = new JTextField();
		txt_anIntArray4805.setColumns(10);
		txt_anIntArray4805.setBounds(95, 137, 263, 20);
		panel_10.add(txt_anIntArray4805);

		JPanel panel_8 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_8, null);
		panel_8.setLayout(null);

        JTextField txtAnobjectarray_8 = new JTextField();
		txtAnobjectarray_8.setBounds(10, 5, 86, 20);
		txtAnobjectarray_8.setText("anObjectArray4774");
		txtAnobjectarray_8.setEditable(false);
		txtAnobjectarray_8.setColumns(10);
		panel_8.add(txtAnobjectarray_8);

		txt_anObjectArray4774 = new JTextField();
		txt_anObjectArray4774.setBounds(106, 5, 372, 20);
		txt_anObjectArray4774.setColumns(10);
		panel_8.add(txt_anObjectArray4774);

        JTextField txtAnobjectarray_9 = new JTextField();
		txtAnobjectarray_9.setText("anObjectArray4803");
		txtAnobjectarray_9.setEditable(false);
		txtAnobjectarray_9.setColumns(10);
		txtAnobjectarray_9.setBounds(10, 30, 86, 20);
		panel_8.add(txtAnobjectarray_9);

		txt_anObjectArray4803 = new JTextField();
		txt_anObjectArray4803.setColumns(10);
		txt_anObjectArray4803.setBounds(106, 30, 372, 20);
		panel_8.add(txt_anObjectArray4803);

        JTextField txtAnobjectarray_10 = new JTextField();
		txtAnobjectarray_10.setText("anObjectArray4680");
		txtAnobjectarray_10.setEditable(false);
		txtAnobjectarray_10.setColumns(10);
		txtAnobjectarray_10.setBounds(10, 55, 86, 20);
		panel_8.add(txtAnobjectarray_10);

		txt_anObjectArray4680 = new JTextField();
		txt_anObjectArray4680.setColumns(10);
		txt_anObjectArray4680.setBounds(106, 55, 372, 20);
		panel_8.add(txt_anObjectArray4680);

        JTextField txtAnobjectarray_11 = new JTextField();
		txtAnobjectarray_11.setText("anObjectArray4856");
		txtAnobjectarray_11.setEditable(false);
		txtAnobjectarray_11.setColumns(10);
		txtAnobjectarray_11.setBounds(10, 82, 86, 20);
		panel_8.add(txtAnobjectarray_11);

		txt_anObjectArray4856 = new JTextField();
		txt_anObjectArray4856.setColumns(10);
		txt_anObjectArray4856.setBounds(106, 82, 372, 20);
		panel_8.add(txt_anObjectArray4856);

        JTextField txtAnobjectarray_12 = new JTextField();
		txtAnobjectarray_12.setText("anObjectArray4852");
		txtAnobjectarray_12.setEditable(false);
		txtAnobjectarray_12.setColumns(10);
		txtAnobjectarray_12.setBounds(10, 107, 86, 20);
		panel_8.add(txtAnobjectarray_12);

		txt_anObjectArray4852 = new JTextField();
		txt_anObjectArray4852.setColumns(10);
		txt_anObjectArray4852.setBounds(106, 107, 372, 20);
		panel_8.add(txt_anObjectArray4852);

        JTextField txtAnobjectarray_13 = new JTextField();
		txtAnobjectarray_13.setText("anObjectArray4711");
		txtAnobjectarray_13.setEditable(false);
		txtAnobjectarray_13.setColumns(10);
		txtAnobjectarray_13.setBounds(10, 133, 86, 20);
		panel_8.add(txtAnobjectarray_13);

		txt_anObjectArray4711 = new JTextField();
		txt_anObjectArray4711.setColumns(10);
		txt_anObjectArray4711.setBounds(106, 133, 372, 20);
		panel_8.add(txt_anObjectArray4711);

        JTextField txtAnobjectarray_14 = new JTextField();
		txtAnobjectarray_14.setText("anObjectArray4753");
		txtAnobjectarray_14.setEditable(false);
		txtAnobjectarray_14.setColumns(10);
		txtAnobjectarray_14.setBounds(10, 158, 86, 20);
		panel_8.add(txtAnobjectarray_14);

		txt_anObjectArray4753 = new JTextField();
		txt_anObjectArray4753.setColumns(10);
		txt_anObjectArray4753.setBounds(106, 158, 372, 20);
		panel_8.add(txt_anObjectArray4753);

		txt_anObjectArray4688 = new JTextField();
		txt_anObjectArray4688.setColumns(10);
		txt_anObjectArray4688.setBounds(106, 184, 372, 20);
		panel_8.add(txt_anObjectArray4688);

        JTextField anObjectArray4775 = new JTextField();
		anObjectArray4775.setText("anObjectArray4775");
		anObjectArray4775.setEditable(false);
		anObjectArray4775.setColumns(10);
		anObjectArray4775.setBounds(10, 215, 86, 20);
		panel_8.add(anObjectArray4775);

		txt_anObjectArray4775 = new JTextField();
		txt_anObjectArray4775.setColumns(10);
		txt_anObjectArray4775.setBounds(106, 215, 372, 20);
		panel_8.add(txt_anObjectArray4775);

        JTextField txtAnobjectarray_15 = new JTextField();
		txtAnobjectarray_15.setText("anObjectArray4688");
		txtAnobjectarray_15.setEditable(false);
		txtAnobjectarray_15.setColumns(10);
		txtAnobjectarray_15.setBounds(10, 184, 86, 20);
		panel_8.add(txtAnobjectarray_15);

		JButton btnAddSprite = new JButton("add sprite");
		btnAddSprite.setBounds(213, 11, 101, 34);
		btnAddSprite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(currentInterface <= 0)
					return;
				ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(6, 38);
				comp.basePositionX = 0;
				comp.basePositionY =0;
				comp.parentId = -1;
				comp.spriteId = 0;
				comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) + (currentInterface << 16);
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), comp.encode());
				ComponentDefinition.getInterface(currentInterface, true);
				drawTree(currentInterface);

			}
		});
		getContentPane().add(btnAddSprite);

		JButton btnAddText = new JButton("add text");
		btnAddText.setBounds(324, 11, 89, 34);
		btnAddText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentInterface <= 0)
					return;
				ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(4, 5);
				comp.basePositionX = 0;
				comp.basePositionY =0;
				comp.parentId = -1;
				comp.text = "Hallo world";
				comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) + (currentInterface << 16);
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), comp.encode());
				ComponentDefinition.getInterface(currentInterface, true);
				drawTree(currentInterface);

			}
		});
		getContentPane().add(btnAddText);

		JButton btnAddContainer = new JButton("add container");
		btnAddContainer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentInterface <= 0)
					return;
				ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(6, 0);
				comp.basePositionX = 0;
				comp.basePositionY =0;
				comp.baseHeight = 50;
				comp.baseWidth = 50;
				comp.parentId = -1;
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), comp.encode());
				ComponentDefinition.getInterface(currentInterface, true);
				drawTree(currentInterface);

			}
		});
		btnAddContainer.setBounds(423, 11, 118, 34);
		getContentPane().add(btnAddContainer);

		JButton btnAddModel = new JButton("add model");
		btnAddModel.setBounds(557, 11, 99, 34);
		btnAddModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentInterface <= 0)
					return;
				ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(732, 3);
				comp.basePositionX = 0;
				comp.basePositionY =0;
				comp.parentId = -1;
				comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) + (currentInterface << 16);
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), comp.encode());
				ComponentDefinition.getInterface(currentInterface, true);
				drawTree(currentInterface);
			}
		});
		getContentPane().add(btnAddModel);

		JButton btnAddRectangle = new JButton("add rectangle");
		btnAddRectangle.setBounds(666, 11, 132, 34);
		btnAddRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentInterface <= 0)
					return;
				ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(640, 0);
				comp.basePositionX = 0;
				comp.basePositionY =0;
				comp.parentId = -1;
				comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) + (currentInterface << 16);
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), comp.encode());
				ComponentDefinition.getInterface(currentInterface, true);
				drawTree(currentInterface);

			}
		});
		getContentPane().add(btnAddRectangle);

        JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Premade components", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(10, 465, 205, 112);
		getContentPane().add(panel_6);
		panel_6.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 16, 193, 85);
		panel_6.add(panel_1);
		panel_1.setLayout(null);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Close button", "Normal button", "Start interface", "Basic custom hover", "Basic button with pop-up"}));
		comboBox.setBounds(0, 21, 193, 20);
		panel_1.add(comboBox);

		JButton btnAddPremadeComponent = new JButton("add premade component");
		btnAddPremadeComponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addDefaultComponent(currentInterface);
			}
		});
		btnAddPremadeComponent.setBounds(0, 52, 193, 23);
		panel_1.add(btnAddPremadeComponent);
		/**
		 * adding a new interface
		 */
		JButton btnTest = new JButton("add new interface");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 36);
				defaultButton.basePositionX = 0;
				defaultButton.basePositionY = 0;
				defaultButton.parentId = -1;
				Cache.STORE.getIndexes()[3].putFile(ComponentDefinition.getInterfaceDefinitionsSize(), 0 , defaultButton.encode());
				ComponentDefinition.icomponentsdefs = new ComponentDefinition[ComponentDefinition.getInterfaceDefinitionsSize()][];
				JList list = new JList(populateList());
				list.addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent evt) {
						if (evt.getValueIsAdjusting())
							return;
						int id  = Integer.parseInt(list.getSelectedValue().toString().replaceAll("Interface: ", ""));
						currentInterface = id;
						drawTree(id);
					}

				});
				scrollPane.setViewportView(list);
			}
		});
		btnTest.setBounds(10, 387, 193, 32);
		getContentPane().add(btnTest);

        JButton btnDeleteSelectedInterface = new JButton("Cleans the current interface");
		btnDeleteSelectedInterface.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog( componentTree,
						"Are you sure that you want to remove interface "+currentInterface+" ?",
						"Inane warning",
						JOptionPane.YES_NO_OPTION);
				if(option == 0) {
					for(int i = 0; i < ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface); i ++){
						if(i == 0){
							addIndex0text((currentInterface));
						} else
							Cache.STORE.getIndexes()[3].removeFile((currentInterface),i);
					}
					try {
						Cache.init();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ComponentDefinition.icomponentsdefs = new ComponentDefinition[ComponentDefinition.getInterfaceDefinitionsSize()][];
					drawTree(currentInterface);
				}
			}
		});
		btnDeleteSelectedInterface.setBounds(10, 419, 193, 32);
		getContentPane().add(btnDeleteSelectedInterface);

        JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(728, 473, 277, 184);
		getContentPane().add(panel_5);
		panel_5.setLayout(null);

		JPanel panel_settings = new JPanel();
		panel_settings.setBounds(6, 28, 259, 149);
		panel_5.add(panel_settings);
		panel_settings.setLayout(null);

		chckbxShowContainers = new JCheckBox("show container frame");
		chckbxShowContainers.setSelected(true);
		chckbxShowContainers.setBounds(6, 59, 193, 23);
		chckbxShowContainers.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					try {
						makeInterface(currentInterface, true,chckbxShowHiddenComps.isSelected(),chckbxShowRectangles.isSelected());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						makeInterface(currentInterface, false,chckbxShowHiddenComps.isSelected(),chckbxShowRectangles.isSelected());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
		panel_settings.add(chckbxShowContainers);

		chckbxShowRectangles = new JCheckBox("show model frame");
		chckbxShowRectangles.setSelected(true);
		chckbxShowRectangles.setBounds(6, 33, 193, 23);
		chckbxShowRectangles.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					try {
						makeInterface(currentInterface, chckbxShowContainers.isSelected(),chckbxShowHiddenComps.isSelected(), true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						makeInterface(currentInterface, chckbxShowContainers.isSelected(),chckbxShowHiddenComps.isSelected(),false);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});
		panel_settings.add(chckbxShowRectangles);

		chckbxShowHiddenComps = new JCheckBox("show hidden comps");
		chckbxShowHiddenComps.setBounds(6, 9, 225, 23);
		panel_settings.add(chckbxShowHiddenComps);

		chckbxRefreshTreeOn = new JCheckBox("refresh componentTree on save");
		chckbxRefreshTreeOn.setSelected(true);
		chckbxRefreshTreeOn.setBounds(6, 83, 225, 23);
		panel_settings.add(chckbxRefreshTreeOn);

		chckbxRealFonttesting = new JCheckBox("real font (testing)");
		chckbxRealFonttesting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				drawTree(currentInterface);
			}
		});
		chckbxRealFonttesting.setBounds(6, 107, 193, 25);
		panel_settings.add(chckbxRealFonttesting);

        JButton btnNewButton_1 = new JButton("Save");

		btnNewButton_1.setBounds(10, 604, 182, 43);
		getContentPane().add(btnNewButton_1);

		txt_qcopy_inter = new JTextField();
		txt_qcopy_inter.setToolTipText("interface id you want to copy from");
		txt_qcopy_inter.setBounds(826, 449, 78, 22);
		getContentPane().add(txt_qcopy_inter);
		txt_qcopy_inter.setColumns(10);

		txt_qcopy_comp = new JTextField();
		txt_qcopy_comp.setBounds(904, 449, 74, 22);
		getContentPane().add(txt_qcopy_comp);
		txt_qcopy_comp.setColumns(10);

		JButton btnQcopy = new JButton("");
		btnQcopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					copiedComp = ComponentDefinition.getInterfaceComponent(Integer.parseInt(txt_qcopy_inter.getText()), Integer.parseInt(txt_qcopy_comp.getText()));
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(scrollPane_2,
							"Component can not be found.");
				}
			}
		});
		btnQcopy.setBounds(990, 445, 27, 25);
		getContentPane().add(btnQcopy);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(currentInterface != -1 && selectedComp != -1) {
					saveInterface(currentInterface,selectedComp);
					drawTree(currentInterface);
					setValues(currentInterface,selectedComp);
				} else {
					JOptionPane.showMessageDialog(scrollPane_2,
							"Please selected a component & interface before saving it.");
				}
			}
		});
		chckbxShowHiddenComps.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					try {
						makeInterface(currentInterface, chckbxShowContainers.isSelected(), true,chckbxShowRectangles.isSelected());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						makeInterface(currentInterface, chckbxShowContainers.isSelected(),false,chckbxShowRectangles.isSelected());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("");
		menuBar.add(mnNewMenu);

		JRadioButtonMenuItem rdbtnmntmRed = new JRadioButtonMenuItem("Container color");
		mnNewMenu.add(rdbtnmntmRed);
		JMenu mnNewMenu_1 = new JMenu("Info");
		mnNewMenu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(scrollPane_2,
						"Interface editor made by Shnek, Discord : Cara Shnek#6969 ");
			}
		});
		menuBar.add(mnNewMenu_1);

		JMenu mnAbout = new JMenu("Extra");
		menuBar.add(mnAbout);

		JMenuItem mntmDumpSprites = new JMenuItem("Dump sprites");
		mntmDumpSprites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressMonitor = new ProgressMonitor(menuBar,
						"Dumping sprites",
						"", 0, Cache.STORE.getIndexes()[8].getLastArchiveId());
				progressMonitor.setProgress(1);

				dumpSprites();
			}
		});
		mnAbout.add(mntmDumpSprites);

		/*mntmPackSprite = new JMenuItem("Pack sprite");
		mntmPackSprite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					try {
						BufferedImage img = ImageIO.read(selectedFile);
						byte[] byteArray = toByteArrayAutoClosable(img, "png");
						Cache.STORE.getIndexes()[8].putFile(Cache.STORE.getIndexes()[5].getLastArchiveId(), 0 , byteArray);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		mnAbout.add(mntmPackSprite);*/

		JMenuItem mntmPackInterface = new JMenuItem("Pack interface");
		mntmPackInterface.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("choosertitle");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					packInterface(chooser.getSelectedFile().getPath());
					// System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
				} else {
					System.out.println("No Selection ");
				}


			}

		});
		mnAbout.add(mntmPackInterface);
		/**
		 *
		 */

	}


	private static byte[] toByteArrayAutoClosable(BufferedImage image, String type) throws IOException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
			ImageIO.write(image, type, out);
			return out.toByteArray();
		}
	}
	/**
	 * set the values to the text inputs
	 * @param inter
	 * @param id
	 */
	public void setValues(int inter,int id){
		/* cleaning previous values*/
		cleanValues();
		/*  selected component*/
		final ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(inter, id);
		/* setting all the values*/
		this.txt_hash.setText(comp.ihash+"");
		this.txt_height.setText(comp.baseHeight+"");
		if(comp.type == ComponentConstants.CONTAINER)
			txt_type.setText("Container");
		else if(comp.type == ComponentConstants.TEXT)
			txt_type.setText("Text");
		else if(comp.type == ComponentConstants.SPRITE)
			txt_type.setText("Sprite");
		else if(comp.type == ComponentConstants.FIGURE)
			txt_type.setText("Figure");
		else if(comp.type == ComponentConstants.MODEL)
			txt_type.setText("Model");
		else
			txt_type.setText(comp.type+"");
		this.txt_scrollX.setText(comp.layerHeight+"");
		this.txt_scrollY.setText(comp.layerWidth+"");
		this.txt_widht.setText(comp.baseWidth+"");
		this.txt_x.setText(comp.basePositionX+"");
		this.txt_y.setText(comp.basePositionY+"");
		this.txt_border.setText(comp.width2+"");
		this.txt_animationId.setText(comp.animationId+"");
		if(comp.hidden)
			chckbxHidden.setSelected(true);
		else
			chckbxHidden.setSelected(false);
		if(comp.hFlip)
			this.chckbxHorizontalFlip.setSelected(true);
		else
			this.chckbxHorizontalFlip.setSelected(false);
		if(comp.vFlip)
			this.chckbxVerticalFlip.setSelected(true);
		else
			this.chckbxVerticalFlip.setSelected(false);
		if(comp.filled)
			this.chckbxFilled.setSelected(true);
		else
			this.chckbxFilled.setSelected(false);

		this.txt_trans.setText(comp.transparency+"");
		this.txt_modeHeight.setText(comp.aspectHeightType+"");
		this.txt_widthMode.setText(comp.aspectWidthType+"");
		this.txt_model.setText(comp.modelId+"");
		this.txt_parent.setText(comp.parentId+"");
		this.txt_sprite.setText(comp.spriteId+"");
		this.txt_color.setText(comp.color+"");
		this.txt_text.setText(comp.text);
		this.txt_multi.setText(comp.multiline+"");
		this.txt_font.setText(comp.fontId+"");
		this.txt_modex.setText(comp.aspectXType+"");
		this.txt_positionmodeY.setText(comp.aspectYType+"");
		this.txt_xali.setText(comp.textHorizontalAli+"");
		this.txt_yali.setText(comp.textVerticalAli+"");
		if(comp.repeat_)
			this.chckbxRepeat.setSelected(true);
		else
			this.chckbxRepeat.setSelected(false);
		this.txt_border.setText(comp.borderThickness+"");

		if(comp.rightclickOptions != null){
			this.txt_leftclick.setText(comp.rightclickOptions[0]);
			if(comp.rightclickOptions.length > 1)
				if(comp.rightclickOptions[1] != null)
					this.txt_option1.setText(comp.rightclickOptions[1]);
			if(comp.rightclickOptions.length > 2)
				if(comp.rightclickOptions[2] != null)
					this.txt_option2.setText(comp.rightclickOptions[2]);
			if(comp.rightclickOptions.length > 3)
				if(comp.rightclickOptions[3] != null)
					this.txt_option3.setText(comp.rightclickOptions[3]);
			if(comp.rightclickOptions.length > 4)
				if(comp.rightclickOptions[4] != null)
					this.txt_option4.setText(comp.rightclickOptions[4]);
			if(comp.rightclickOptions.length > 5)
				if(comp.rightclickOptions[5] != null)
					this.txt_option5.setText(comp.rightclickOptions[5]);
		}
		if(comp.onMouseHoverScript != null){
			String values = "";
			for(Object o : comp.onMouseHoverScript){
				values+=o+";";
			}
			txt_fullonhover.setText(values);
		}
		if(comp.onMouseLeaveScript != null){
			String values = "";
			for(Object o : comp.onMouseLeaveScript){
				values+=o+";";
			}
			txt_mouseLeave.setText(values);
		}
		if(comp.onLoadScript != null){
			String values = "";
			for(Object o : comp.onLoadScript){
				values+=o+";";
			}
			this.txt_onload.setText(values);
		}
		//anObjectArray4770
		if(comp.anObjectArray4770 != null){
			String values = "";
			for(Object o : comp.anObjectArray4770){
				values+=o+";";
			}
			this.txt_anObjectArray4770.setText(values);
		}
		if(comp.anObjectArray4771 != null){
			String values = "";
			for(Object o : comp.anObjectArray4771){
				values+=o+";";
			}
			this.txt_anObjectArray4771.setText(values);
		}
		if(comp.anObjectArray4768 != null){
			String values = "";
			for(Object o : comp.anObjectArray4768){
				values+=o+";";
			}
			this.txt_anObjectArray4768.setText(values);
		}
		if(comp.anObjectArray4807 != null){
			String values = "";
			for(Object o : comp.anObjectArray4807){
				values+=o+";";
			}
			this.txt_anObjectArray4807.setText(values);
		}
		if(comp.popupScript != null){
			String values = "";
			for(Object o : comp.popupScript){
				values+=o+";";
			}

			this.txt_popup.setText(values);
		}
		if(comp.anObjectArray4742 != null){
			String values = "";
			for(Object o : comp.anObjectArray4742){
				values+=o+";";
			}
			this.txt_anObjectArray4742.setText(values);
		}

		if(comp.anObjectArray4788 != null){
			String values = "";
			for(Object o : comp.anObjectArray4788){
				values+=o+";";
			}
			this.txt_anObjectArray4788.setText(values);
		}

		if(comp.anObjectArray4701 != null){
			String values = "";
			for(Object o : comp.anObjectArray4701){
				values+=o+";";
			}
			this.txt_anObjectArray4701.setText(values);
		}

		if(comp.anObjectArray4774 != null){
			String values = "";
			for(Object o : comp.anObjectArray4774){
				values+=o+";";
			}
			this.txt_anObjectArray4774.setText(values);
		}
		if(comp.anObjectArray4803 != null){
			String values = "";
			for(Object o : comp.anObjectArray4803){
				values+=o+";";
			}
			this.txt_anObjectArray4803.setText(values);
		}
		if(comp.anObjectArray4680 != null){
			String values = "";
			for(Object o : comp.anObjectArray4680){
				values+=o+";";
			}
			this.txt_anObjectArray4680.setText(values);
		}
		if(comp.anObjectArray4856  != null){
			String values = "";
			for(Object o : comp.anObjectArray4856 ){
				values+=o+";";
			}
			this.txt_anObjectArray4856.setText(values);
		}
		if(comp.anObjectArray4852 != null){
			String values = "";
			for(Object o : comp.anObjectArray4852){
				values+=o+";";
			}
			this.txt_anObjectArray4852.setText(values);
		}
		if(comp.	anObjectArray4711 != null){
			String values = "";
			for(Object o : comp.	anObjectArray4711){
				values+=o+";";
			}
			this.txt_anObjectArray4711.setText(values);
		}

		if(comp.anObjectArray4753 != null){
			String values = "";
			for(Object o : comp.anObjectArray4753){
				values+=o+";";
			}
			this.txt_anObjectArray4753.setText(values);
		}
		if(comp.anObjectArray4688 != null){
			String values = "";
			for(Object o : comp.anObjectArray4688){
				values+=o+";";
			}
			this.txt_anObjectArray4688.setText(values);
		}
		if(comp.anObjectArray4775 != null){
			String values = "";
			for(Object o : comp.anObjectArray4775){
				values+=o+";";
			}
			this.txt_anObjectArray4775.setText(values);
		}
		/**
		 * configs
		 */
		if(comp.configs != null){
			String values = "";
			for(int i : comp.configs){
				values+= i;
			}
			this.txt_configs.setText(values);
		}

		if(comp.anIntArray4833 != null){
			String values = "";
			for(int i : comp.anIntArray4833){
				values+= i;
			}
			this.txt_anIntArray4833.setText(values);
		}
		if(comp.anIntArray4789 != null){
			String values = "";
			for(int i : comp.anIntArray4789){
				values+= i;
			}
			this.txt_anIntArray4789.setText(values);
		}
		if(comp.anIntArray4829 != null){
			String values = "";
			for(int i : comp.anIntArray4829){
				values+= i;
			}
			this.txt_anIntArray4829.setText(values);
		}
		if(comp.anIntArray4805 != null){
			String values = "";
			for(int i : comp.anIntArray4805){
				values+= i;
			}
			this.txt_anIntArray4805.setText(values);
		}
		/*
		 * drawing rec
		 */
		/*this.panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
					g.setColor(Color.WHITE);
					g.drawImage(result, 0, 0, null);
					ComponentPosition.setValues(comp);
					g.drawRect(ComponentDefinition.getX(comp, inter), ComponentDefinition.getY(comp, inter), comp.width, comp.height);

				}
		};*/
		drawSelected(comp, inter);
		scrollPane_2.setViewportView(panel);
	}

	private void drawSelected(ComponentDefinition comp, int interafece) {
		this.panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.drawImage(result, 0, 0, null);
				ComponentPosition.setValues(comp);
				g.drawRect(ComponentDefinition.getX(comp, interafece), ComponentDefinition.getY(comp, interafece), comp.width, comp.height);
			}
		};
	}

	/**
	 * cleans all the input of a giving component container
	 * @param container
	 */
	private void cleanTab(Container container){
		for(Component c : container.getComponents())
			if(c instanceof JTextField && c.isEnabled())
			{
				JTextField ctrl = (JTextField) c;
				if(ctrl.isEditable())
					ctrl.setText(null);
			}
	}
	/**
	 * cleans all the values
	 */
	public void cleanValues(){
		for(int i = 0; i < tabbedPane.getTabCount(); i++) {
			cleanTab((Container) tabbedPane.getComponentAt(i));
		}
	}
	/**
	 * saves the interface
	 * @param inter
	 * @param comp
	 */
	public void saveInterface(int inter, int comp){
		ComponentDefinition changedComponent = ComponentDefinition.getInterfaceComponent(inter, comp);
		changedComponent.basePositionX = Integer.parseInt(txt_x.getText());
		changedComponent.basePositionY = Integer.parseInt(txt_y.getText());
		changedComponent.baseHeight = Integer.parseInt(txt_height.getText());
		changedComponent.baseWidth = Integer.parseInt(this.txt_widht.getText());
		changedComponent.parentId = Integer.parseInt(this.txt_parent.getText());
		changedComponent.color = Integer.parseInt(this.txt_color.getText());
		changedComponent.aspectXType = (byte)Integer.parseInt(this.txt_modex.getText());
		changedComponent.aspectYType = (byte)Integer.parseInt(this.txt_positionmodeY.getText());
		if(changedComponent.type == ComponentConstants.SPRITE){
			changedComponent.spriteId = Integer.parseInt(this.txt_sprite.getText());
		}
		/*
		 * this.txt_scrollX.setText(comp.layerHeight+"");
			this.txt_scrollY.setText(comp.layerWidth+"");
		 */
		changedComponent.layerHeight = Integer.parseInt(this.txt_scrollX.getText());
		changedComponent.layerWidth = Integer.parseInt(this.txt_scrollY.getText());
		changedComponent.aspectXType = Byte.parseByte(this.txt_modex.getText());
		changedComponent.aspectYType = Byte.parseByte(this.txt_positionmodeY.getText());
		changedComponent.text = this.txt_text.getText();
		if(!this.txt_font.getText().isEmpty())
			changedComponent.fontId = Integer.parseInt(this.txt_font.getText());
		if(!this.txt_animationId.getText().isEmpty())
			changedComponent.animationId = Integer.parseInt(this.txt_animationId.getText());
		changedComponent.hidden = chckbxHidden.isSelected();
		changedComponent.hFlip = this.chckbxHorizontalFlip.isSelected();
		changedComponent.vFlip = this.chckbxVerticalFlip.isSelected();
		changedComponent.repeat_ = changedComponent.repeat_;//this.chckbxRepeat.isSelected();
		changedComponent.modelId = Integer.parseInt(this.txt_model.getText());
		changedComponent.aspectHeightType = (byte) Integer.parseInt(this.txt_modeHeight.getText());
		changedComponent.aspectWidthType = (byte)Integer.parseInt(this.txt_widthMode.getText());
		changedComponent.transparency = Integer.parseInt(this.txt_trans.getText());
		changedComponent.filled = this.chckbxFilled.isSelected();
		changedComponent.textHorizontalAli = Integer.parseInt(this.txt_xali.getText());
		changedComponent.textVerticalAli = Integer.parseInt(this.txt_yali.getText());
		/**
		 * saving texts
		 * TODO more options
		 */
		if(!this.txt_leftclick.getText().isEmpty()){
			if(changedComponent.rightclickOptions == null)
				changedComponent.rightclickOptions = new String[5];
			changedComponent.optionMask = ComponentConstants.CLICK_MASK;
			changedComponent.rightclickOptions[0] = this.txt_leftclick.getText();
		}
		/**
		 * saving scripts
		 */
		changedComponent.popupScript = InterfaceUtils.getScriptArray(this.txt_popup.getText());
		changedComponent.onMouseHoverScript = InterfaceUtils.getScriptArray(this.txt_fullonhover.getText());
		changedComponent.onMouseLeaveScript = InterfaceUtils.getScriptArray(this.txt_mouseLeave.getText());
		changedComponent.anObjectArray4770 = InterfaceUtils.getScriptArray(this.txt_anObjectArray4770.getText());
		changedComponent.anObjectArray4788 = InterfaceUtils.getScriptArray(this.txt_anObjectArray4788.getText());
		changedComponent.anObjectArray4768 = InterfaceUtils.getScriptArray(this.txt_anObjectArray4768.getText());
		changedComponent.anObjectArray4807 = InterfaceUtils.getScriptArray(this.txt_anObjectArray4807.getText());
		changedComponent.anObjectArray4742 = InterfaceUtils.getScriptArray(this.txt_anObjectArray4742.getText());
		changedComponent.anObjectArray4701 = InterfaceUtils.getScriptArray(this.txt_anObjectArray4701.getText());
		changedComponent.onLoadScript = InterfaceUtils.getScriptArray(this.txt_onload.getText());
		changedComponent.anObjectArray4771 = InterfaceUtils.getScriptArray(this.txt_anObjectArray4771.getText());
		changedComponent.configs = InterfaceUtils.getConfigArray(this.txt_configs.getText());
		//message
		//JOptionPane.showMessageDialog(scrollPane_2, "Component has been succesfully saved.");
		//saves it
		Cache.STORE.getIndexes()[3].putFile(inter, comp, changedComponent.encode());
	}
	/**
	 * fills the list
	 * @return
	 */
	public DefaultListModel  populateList(){
		DefaultListModel listModel = new DefaultListModel();
		for(int i = 0; i < ComponentDefinition.getInterfaceDefinitionsSize(); i ++){
			try{
				if(ComponentDefinition.getInterface(i) != null) {
					listModel.addElement("Interface: " + i);
				}
			} catch(Exception ex){

			}
		}
		return listModel;

	}
	public DefaultTreeModel populateTree(int interfaceId){
		DefaultMutableTreeNode head =  new DefaultMutableTreeNode("Interface "+interfaceId+"");
		for(ComponentDefinition component : ComponentDefinition.getInterface(interfaceId)){
			if(component.parentId == -1){
				head.add(new DefaultMutableTreeNode("Component: "+component.componentId));
				continue;
			}
			DefaultMutableTreeNode  child = new DefaultMutableTreeNode("Component "+ component.componentId);
			ArrayList<ComponentDefinition> childs  = ComponentDefinition.getChildsByParent(interfaceId, component.ihash);
			while(childs != null){
				for(ComponentDefinition childeren : childs) {
					child.add(new DefaultMutableTreeNode("Component:" + component.componentId));

				}
				head.add(child);


			}
		}
		return new DefaultTreeModel(head);
	}
	/**
	 * TODO rewrite this
	 * fills the jtree
	 * @param interfaceId
	 * @return
	 */
	public  DefaultTreeModel createInterfaceTree(int interfaceId){
		DefaultMutableTreeNode inter =  new DefaultMutableTreeNode("Interface "+interfaceId+"");
		//new stuff
		for(int i = 0; i < ComponentDefinition.getInterfaceDefinitionsComponentsSize(interfaceId); i ++){
			ComponentDefinition c = ComponentDefinition.getInterfaceComponent(interfaceId, i);
			if(c == null) {
				System.out.println("is null" +i);
				continue;
			}
			System.out.println("here");
			//check for the base containers
			if(c.parentId == -1 && ComponentDefinition.hasChilds(interfaceId, c.ihash)){
				DefaultMutableTreeNode  child = new DefaultMutableTreeNode("Component "+ c.componentId);
				ArrayList<ComponentDefinition> childs  = ComponentDefinition.getChildsByParent(interfaceId, c.ihash);
				//loop throu the childs
				for(ComponentDefinition c2 : childs){
					//check if the child is a container , if so loop throu his childs etc..
					if(ComponentDefinition.hasChilds(interfaceId, c2.ihash)){
						ArrayList<ComponentDefinition> childs2  = ComponentDefinition.getChildsByParent(interfaceId, c2.ihash);
						DefaultMutableTreeNode  container2 = new DefaultMutableTreeNode("Component "+c2.componentId);
						for(ComponentDefinition c3 : childs2){
							if(ComponentDefinition.hasChilds(interfaceId, c3.ihash)){
								ArrayList<ComponentDefinition> childs3  = ComponentDefinition.getChildsByParent(interfaceId, c3.ihash);
								DefaultMutableTreeNode  container3 = new DefaultMutableTreeNode("Component "+c3.componentId);
								for(ComponentDefinition c4 : childs3){
									if(ComponentDefinition.hasChilds(interfaceId, c4.ihash)){
										ArrayList<ComponentDefinition> child4  = ComponentDefinition.getChildsByParent(interfaceId, c4.ihash);
										DefaultMutableTreeNode  container4 = new DefaultMutableTreeNode("Component "+c4.componentId);
										for(ComponentDefinition c5 : child4){
											DefaultMutableTreeNode  childs4 = new DefaultMutableTreeNode("Component "+c5.componentId);
											container4.add(childs4);
										}

										container3.add(container4);
									} else {
										DefaultMutableTreeNode  child2 = new DefaultMutableTreeNode("Component "+c4.componentId);
										container3.add(child2);
									}
								}
								container2.add(container3);
							} else {
								DefaultMutableTreeNode  child2 = new DefaultMutableTreeNode("Component "+c3.componentId);
								container2.add(child2);
							}
						}
						child.add(container2);
					}
					else {
						DefaultMutableTreeNode  child2 = new DefaultMutableTreeNode("Component "+c2.componentId);
						child.add(child2);
					}
				}

				inter.add(child );
			} else if(c.parentId == -1 && !ComponentDefinition.hasChilds(interfaceId, c.ihash)){
				DefaultMutableTreeNode  child2 = new DefaultMutableTreeNode("Component "+c.componentId);
				inter.add(child2);
			}

		}

		return new DefaultTreeModel(inter);

	}

	public void dumpSprites() {
		File directory = new File(PropertyValues.dump_path);

		if (!directory.exists()) {
			directory.mkdirs();
		}
		try {
			com.rs.cache.Cache.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int size = Cache.STORE.getIndexes()[8].getLastArchiveId();
		for (int i = 0; i < Cache.STORE.getIndexes()[8].getLastArchiveId(); i++) {
			progressMonitor.setProgress(i + 1);
			if (Cache.STORE.getIndexes()[8].getFile(i) == null)
				continue;
			byte[] data = Cache.STORE.getIndexes()[8].getFile(i);
			if(data == null)
				continue;
			ByteBuffer buf = ByteBuffer.wrap(data);
			Sprite sprite = Sprite.decode(buf);
			if(sprite == null)
				continue;
			for (int frame = 0; frame < sprite.size(); frame++) {
				File file = new File(PropertyValues.dump_path, i + "_" + frame + ".png");
				BufferedImage image = sprite.getFrame(frame);

				try {
					ImageIO.write(image, "png", file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			double progress = (double) (i + 1) / size * 100;

			System.out.printf("%d out of %d %.2f%s\n", (i + 1),size, progress, "%");

		}


	}

	public void addIndex0text(int interfaceId){
		ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 19);
		defaultButton.parentId = -1;
		defaultButton.text = "I'm cleaned :)";
		Cache.STORE.getIndexes()[3].putFile(interfaceId, 0 , defaultButton.encode());
	}
	public void pastComponent2(){
		if(copiedComp == null){
			JOptionPane.showMessageDialog(scrollPane_2, "No component was selected to paste.");
			return;
		}
		ArrayList<ComponentDefinition> childeren = ComponentDefinition.getChildsByParent(copiedComp.interfaceId, copiedComp.ihash);
		copiedComp.parentId = -1;
		Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), copiedComp.encode());
		int baseParentPosition  = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface);
		if(childeren.size() > 0){ //if component is a container
			for(ComponentDefinition component: childeren){
				component.parentId = baseParentPosition + (currentInterface << 16);
				if(component.type ==  0){

				} else {
					Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) , component.encode());
				}
			}
		}
		drawTree(currentInterface);

	}
	/**
	 * handles the component pasting
	 */
	public void pasteComponent(){
		if(copiedComp == null){
			JOptionPane.showMessageDialog(scrollPane_2, "No component was selected to paste.");
			return;
		}
		if(copiedComp.type == ComponentConstants.CONTAINER){
			int containerPlace = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface);
			copiedComp.parentId = -1;
			Cache.STORE.getIndexes()[3].putFile(currentInterface,  containerPlace, copiedComp.encode());
			ArrayList<ComponentDefinition> childs = ComponentDefinition.getChildsByParent(copiedComp.interfaceId, copiedComp.ihash);
			for(ComponentDefinition c : childs) {
				if(c.type == ComponentConstants.CONTAINER){ //TODO packing containers in containers

				} else
					Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) , c.encode());
			}
			ComponentDefinition.getInterface(currentInterface, true);
			int size  = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface);
			ComponentDefinition parent = ComponentDefinition.getInterfaceComponent(currentInterface, containerPlace);
			for(int i = size - childs.size() -1; i < size; i ++){
				ComponentDefinition component = ComponentDefinition.getInterfaceComponent(currentInterface, i);
				if(component.type != 0){
					component.parentId = parent.ihash;
					Cache.STORE.getIndexes()[3].putFile(currentInterface, i , component.encode());

				}
			}
			ComponentDefinition.getInterface(currentInterface, true);
			drawTree(currentInterface);
		} else {
			copiedComp.parentId = -1;
			Cache.STORE.getIndexes()[3].putFile(currentInterface,   ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), copiedComp.encode());
			ComponentDefinition.getInterface(currentInterface, true);
			drawTree(currentInterface);
		}
	}
	/**
	 * export to interface to a basic file
	 * @param interfaceId
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void exportInterface(int interfaceId) throws FileNotFoundException, IOException{
		File file = new File("data/export/"+interfaceId+".dat");
		byte[] data = Cache.STORE.getIndexes()[3].getArchive(interfaceId).getData();
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(data);
			fos.close();
			//message
			JOptionPane.showMessageDialog(scrollPane_2, interfaceId+" dumped to data/export/"+interfaceId+".dat");
		}
	}

	/**
	 * todo
	 * @param path
	 */
	public void packInterface(String path){
		byte[] data;
		try {
			data = Files.readAllBytes(new File(path).toPath());
			int archiveId = ComponentDefinition.getInterfaceDefinitionsSize();
			ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 19);
			defaultButton.parentId = -1;
			defaultButton.text = "I'm cleaned :)";
			Cache.STORE.getIndexes()[3].putFile(archiveId, 0 , defaultButton.encode());
			Cache.getStore().getIndexes()[3].getArchive(archiveId).setData(data);
			this.drawTree(archiveId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * returns them in the right order
	 * @param interfaceId
	 * @return sorted comp list
	 */
	public ArrayList<ComponentDefinition> getOrderedComps(int interfaceId){
		ArrayList<ComponentDefinition> comps = new ArrayList();
		ArrayList<ComponentDefinition> comps2 = new ArrayList();
		ArrayList<ComponentDefinition> containers = ComponentDefinition.getInterfaceContainers(interfaceId); //gets all the containers of an interface
		ComponentDefinition[] allComps = ComponentDefinition.getInterface(interfaceId);

		if(allComps == null)
			return null;
		for(ComponentDefinition c : allComps){
			if(c == null)
				continue;
			if(c.parentId == -1)
				comps.add(c);
		}
		for(ComponentDefinition comp : containers){
			if(!comps.contains(comp))
				comps.add(comp); //add container itself
			for(ComponentDefinition child : ComponentDefinition.getChildsByParent(interfaceId, comp.ihash))
				comps.add(child); //Add childs

		}
		/**
		 * adding all the comps who don't have a parent
		 */
		for(int i = 0; i< allComps.length; i++){
			if(allComps[i] == null)
				continue;
			ComponentPosition.setValues(allComps[i]);
			boolean found = false;
			for(ComponentDefinition c : comps){
				if(c.componentId == allComps[i].componentId)
					found = true;
			}
			if(!found)
				comps.add(allComps[i]);
		}
		return comps;

	}
	public void drawTree(int id){
		try {
			this.makeInterface(id, chckbxShowContainers.isSelected(),this.chckbxShowHiddenComps.isSelected(),chckbxShowRectangles.isSelected());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		if(chckbxRefreshTreeOn.isSelected()){
			JTree tree = new JTree(createInterfaceTree(id));
			tree.addTreeSelectionListener(e -> {
				DefaultMutableTreeNode selectedNode =  (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				try {
					if(selectedNode.getUserObject() != null){
						int id1 = Integer.parseInt(selectedNode.getUserObject().toString().replaceAll("Component ", ""));
						selectedComp = id1;
						setValues(currentInterface, id1);
					}
				} catch(Exception ex) {
					/* some roots aren't a root , better catch them instead of spamming console*/

				}

			});
			scrollPane_1.setViewportView(tree);
		}
	}
	/**
	 * default components
	 * @param id
	 */
	public void addDefaultComponent(int id){
		switch(comboBox.getSelectedIndex()){
			case 0://default close button (with hover)
				ComponentDefinition defaultcloseButton = ComponentDefinition.getInterfaceComponent(6, 36);
				defaultcloseButton.parentId = -1;
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), defaultcloseButton.encode());
				ComponentDefinition.getInterface(currentInterface, true); //since we need to reload the array
				drawTree(currentInterface);
				break;
			case 1: //normal hover button
				ComponentDefinition container = ComponentDefinition.getInterfaceComponent(506, 1);
				ComponentDefinition text = ComponentDefinition.getInterfaceComponent(506, 2);
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), container.encode());
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), text.encode());
				ComponentDefinition.getInterface(currentInterface, true); //since we need to reload the array
				ComponentDefinition n = ComponentDefinition.getInterfaceComponent(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) -2);
				ComponentDefinition xd = ComponentDefinition.getInterfaceComponent(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) -1);
				xd.parentId = n.ihash;
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface) -1, xd.encode());
				ComponentDefinition.getInterface(currentInterface, true); //since we need to reload the array
				drawTree(currentInterface);
				break;
			case 2: //basic starter interface
				ComponentDefinition basic =  ComponentDefinition.getInterfaceComponent(6, 0);
				int place  =  ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface);
				Cache.STORE.getIndexes()[3].putFile(currentInterface, place, basic.encode());
				for(ComponentDefinition comp : ComponentDefinition.getChildsByParent(6, ComponentDefinition.getInterfaceComponent(6, 0).ihash)){
					if(comp.text.toLowerCase().contains("brimhaven"))
						comp.text = "My starter interface";
					comp.parentId++;
					Cache.STORE.getIndexes()[3].putFile(currentInterface,  ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), comp.encode());
				}
				ComponentDefinition.getInterface(currentInterface, true); //since we need to reload the array
				drawTree(currentInterface);
				break;
			case 3:
				ComponentDefinition hover = ComponentDefinition.getInterfaceComponent(6, 36);
				hover.parentId = -1;
				hover.popupScript = null;
				hover.anObjectArray4770 = null;
				hover.spriteId = 0;
				hover.onMouseHoverScript[2] = 1;
				hover.onMouseLeaveScript[2] = 0;
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), hover.encode());
				ComponentDefinition.getInterface(currentInterface, true); //since we need to reload the array
				drawTree(currentInterface);
				break;
			case 4:
				ComponentDefinition popupButton = ComponentDefinition.getInterfaceComponent(762, 33);
				ComponentDefinition cont = ComponentDefinition.getInterfaceComponent(762, 119);
				cont.parentId = -1;
				popupButton.parentId = -1;
				int place2 = ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface);
				Cache.STORE.getIndexes()[3].putFile(currentInterface, place2, cont.encode());
				popupButton.popupScript[3] = "Click here to change your preset settings.";
				popupButton.onMouseLeaveScript[2] = ComponentDefinition.getInterfaceComponent(currentInterface, place2).ihash;
				popupButton.popupScript[2] = ComponentDefinition.getInterfaceComponent(currentInterface, place2).ihash;
				Cache.STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(currentInterface), popupButton.encode());
				ComponentDefinition.getInterface(currentInterface, true); //since we need to reload the array
				drawTree(currentInterface);
				break;
		}
	}


	/**
	 * draws the interface on the panel
	 * @param interfaceId
	 * @throws IOException
	 * TODO remove all the booleans, shitcode lol

	 */
	public void makeInterface(int interfaceId, boolean showContainers, boolean showHidden, boolean showModels) throws IOException{
		/* graphic part*/
		result = new BufferedImage(panel.getWidth(), panel.getHeight(),   BufferedImage.TYPE_INT_RGB);
        /**
         * drawing
         **/
        Graphics g = result.getGraphics();
		/**
		 * make sure you get them in the right order (containers)
		 */
		List<ComponentDefinition> test = this.getOrderedComps(interfaceId);
		if(test == null) {
			System.out.println("is null");
			return;
		}
		for(ComponentDefinition component : test){
			ComponentPosition.setValues(component);
			/**
			 * if hidden or no null
			 */
			if(component == null)
				continue;
			if(InterfaceUtils.isHidden(component) && !showHidden)
				continue;
			/* vars */
			int width = component.width;
			int height = component.height;
			int x  = ComponentDefinition.getX(component, interfaceId);
			int y  = ComponentDefinition.getY(component, interfaceId);
			/**
			 * if parent is hidden
			 * TODO parent of parent
			 */

			ComponentDefinition parent = InterfaceUtils.getParent(component.parentId);//ComponentDefinition.getParent(component, interfaceId);
			if(parent == null)
				continue;
			/* setting correct values of the parent ofcourse*/
			if(parent != null) {
				ComponentPosition.setValues(parent);

				if(width > parent.width)
					width = parent.width;
				if(height > parent.height)
					height = parent.height;
				if (component.positionX < 0)
					component.positionX = 0;
				if ((component.positionX + component.width) > parent.width)
					component.positionX = (parent.width - component.width);
				if (component.positionY < 0)
					component.positionY = 0;
				if ((component.positionY + component.height) > parent.height)
					component.positionY = (parent.height - component.height);
			}
			/**
			 * checks if it's a sprite
			 */
			if(component.type == ComponentConstants.SPRITE && component.spriteId > -1){
				BufferedImage sprite = ImageUtils.resize(ImageIO.read(new File(PropertyValues.sprite_path+component.spriteId+"_0.png")),width,height);

				/* horizontal flip*/
				if(component.hFlip)
					sprite = ImageUtils.horizontalFlip(sprite);
				/* vertical flip*/
				if(component.vFlip)
					sprite =  ImageUtils.verticalFlip(sprite);
				g.drawImage(sprite, x, y, null);

			}

			/**
			 * Rectangles
			 */
			if(component.type == ComponentConstants.FIGURE) {
				//don't draw them
				if(component.color == 0){
					g.setColor(Color.black);
					//continue;
				} else {
					/** Setting the color **/
					Color color = new Color(component.color);
					int red = color.getRed();
					int green = color.getGreen();
					int blue = color.getBlue();
					g.setColor(new Color(red,green,blue));
				}
				g.drawRect(ComponentDefinition.getX(component, currentInterface), ComponentDefinition.getY(component,currentInterface), component.width, component.height);
				if(component.filled)
					g.fillRect(ComponentDefinition.getX(component, currentInterface), ComponentDefinition.getY(component,currentInterface), component.width, component.height);
				//g.setColor(Color.green);
				//g.drawRect(component.positionX, component.positionY, component.width, component.height);
			}
			/**
			 * models
			 */
			if(component.type == ComponentConstants.MODEL && showModels){
				g.setColor(Color.BLUE);
				g.drawRect(ComponentDefinition.getX(component, interfaceId), ComponentDefinition.getY(component, interfaceId), component.width, component.height);

			}
			/**
			 * Containers
			 */
			if(component.type == ComponentConstants.CONTAINER && showContainers){
				g.setColor(Color.RED);
				if(component.parentId > 0){

					g.drawRect(ComponentDefinition.getX(component, interfaceId), ComponentDefinition.getY(component, interfaceId), component.width, component.height);
				}else {
					g.setColor(Color.green);
					g.drawRect(component.positionX, component.positionY, component.width, component.height);

				}
			}
			/**
			 * checks if it's text
			 * TODO make it written by container some text doesn't get shown because it's under the other sprite
			 */
			if(component.type == ComponentConstants.TEXT){
				FontMetrics fm = g.getFontMetrics();
				Rectangle2D rect = fm.getStringBounds(component.text, g);
				/**
				 * color of the text
				 */
				Color color = new Color(component.color);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				g.setColor(new Color(red,green,blue));
				/** setting font **/
				g.setFont(new Font("Helvetica",0,11));
				if(component.parentId == -1){
					g.drawString(component.text, (int) (component.positionX + component.width/2 - rect.getWidth()/2), (int) (component.positionY + component.height/2 + rect.getHeight()/2));
				} else {
					ComponentPosition.setValues(parent);

					//text in buttons, to center it lol capypasta
					if(component.baseWidth == 0 && component.baseHeight == 0){
						FontMetrics metrics = g.getFontMetrics(new Font("Helvetica",0,11));
						// Determine the X coordinate for the text
						int x2 = parent.positionX + (parent.width - metrics.stringWidth(component.text)) / 2;
						// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
						int y2 = parent.positionY + ((parent.height - metrics.getHeight()) / 2) + metrics.getAscent();
						// Set the font
						g.setFont(new Font("Helvetica",0,11));
						// Draw the String
						g.drawString(component.text, x2, y2);
					} else {
						/*position*/
						int positionX = ComponentDefinition.getX(component,interfaceId);
						int positionY = ComponentDefinition.getY(component,interfaceId);
						/*  not drAWING OUTSIDE THE CONTAINER*/
						if(positionX > parent.width + parent.positionX)
							positionX = parent.width - component.width;
						// if(positionY > parent.height + parent.positionY)
						//positionY = parent.height - component.height;
						g.drawString(component.text, (int) (positionX + component.width/2 - rect.getWidth()/2), (int) (positionY + component.height/2 + rect.getHeight()/2));

					}
				}
				/**
				 * special
				 */
				if(component.text.contains("</u>")){

				}
				/**
				 * testing
				 */
				if(this.chckbxRealFonttesting.isSelected()) {
					int positionX = ComponentDefinition.getX(component,interfaceId);
					int positionY = ComponentDefinition.getY(component,interfaceId);
					int startX = (int) (positionX + component.width/2 - rect.getWidth()/2);
					for(BufferedImage im : FontDecoding.getTextArray(component)) {
						g.drawImage(ImageUtils.colorImage(im, color),  startX, (int) (positionY + component.height/2 + rect.getHeight()/2), null);
						startX += im.getWidth()/2;
					}
				}
			}


		}
		JLabel jLabel = new JLabel(new ImageIcon(result));
		JPanel jPanel = new JPanel();
		jPanel.add(jLabel);
		//close session
		//  g.dispose();

		scrollPane_2.setViewportView(jLabel);
		this.getContentPane().add(jPanel);
	}
}

