package Datenmodell;

import java.awt.GridLayout;
import java.nio.channels.NonWritableChannelException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import Uebung04.A1;
import turban.utils.ErrorHandler;

public class ButtonPanel extends JPanel {

	JButton _startButton;
	JButton _stopButton;
	JButton _pauseButton;
	JProgressBar _progressBar;
	
	private volatile boolean stopTest;
	private boolean pauseTests;
	
	public ButtonPanel(){
		super();
		
		
		_startButton = new JButton("Start");
		_pauseButton = new JButton("Pause");
		_stopButton = new JButton("Stop");
		_progressBar = new JProgressBar();
		
		super.setLayout(new GridLayout(2, 3));
		super.add(_startButton);
		super.add(_progressBar);
		super.add(_stopButton);
		super.add(_pauseButton);
		
		_startButton.addActionListener((ActionEvent)->{
			start();
		});
		
		_pauseButton.addActionListener((ActionEvent) ->{
			pause();
		});
		
		_stopButton.addActionListener((ActionEvent) ->{
			stop();
		});
		
	}

	private void stop() {
		// TODO Auto-generated method stub
		
	}

	private void pause() {
		//je nach Zustand -> Tests weitermachen oder nicht
	}

	private void start() {
		
		Thread th = new Thread(()->{
			try {

			stopTest = false;
			
			if(stopTest = false) {
				//Tests durchführen

			}
			
			
			}catch(Throwable thr) {
				ErrorHandler.logException(thr, ButtonPanel.class, "Fehler!", thr);
				System.out.println("InterruptedException reached!");
			}
		
		
	});
	
	th.start();
		
		// TODO hier die tests starten test s
		
	}
}
