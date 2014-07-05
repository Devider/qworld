package org.nn.world.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class HiddenLayersPanel extends JPanel {
	
	private JComboBox<Integer> hiddenLayers = new JComboBox<Integer>();
	
	public HiddenLayersPanel(){
		setLayout(new  BoxLayout(this, BoxLayout.Y_AXIS));
		hiddenLayers.addItem(1);
		hiddenLayers.addItem(2);
		hiddenLayers.addItem(3);
		hiddenLayers.setSelectedIndex(1);
		add(hiddenLayers);
		hiddenLayers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int layersCount = hiddenLayers.getItemAt(hiddenLayers.getSelectedIndex());
				System.out.println(layersCount);
				System.out.println("count: " + getComponentCount());
				int componentsCount = getComponentCount();
				for (int i = 1; i < componentsCount; i++){
					remove(i);
					System.out.println("removing: " + i + " / " + getComponentCount());
					doLayout();
				}
				for ( int i = 0; i < layersCount; i++) {
					System.out.println("__");
					JSlider slider = new JSlider(4, 14, 5);
					slider.setPaintLabels(true);
					slider.setPaintTrack(true);
					slider.setPaintTicks(true);
					add(slider);
					doLayout();
				}
			}
		});
	}
}
