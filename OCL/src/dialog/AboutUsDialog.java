package dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutUsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	public AboutUsDialog() {
		setAlwaysOnTop(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("res\\Actions-help-about-icon.png"));
		setTitle("About us");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setPreferredSize(new Dimension(450, 300));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel label = new JLabel();
			contentPanel.add(label, BorderLayout.CENTER);
			label.setText(
					"<html><p dir='rtl' style='box-sizing: border-box; margin: 0px 0px 10px; color: rgb(56, 64, 71); font-family: Tahoma; font-size: 16px; line-height: 20px;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>سرویس چاپ آنلاین (Onchapline) تحت پروژه ی&nbsp;<strong style='box-sizing: border-box;'>سردار</strong>&nbsp;توسط جمعی از دانشجویان دانشگاه گیلان طراحی شد. این پروژه از ۵ تیر ماه سال ۱۳۹۴ آغاز گشته طی ۲ متوالی در ۶ شهریور سال ۱۳۹۴ آماده بهره برداری گشت. امید است که بتوان با اجرای کامل این پروژه، خدمتی نو در عرصه رایانه و ارتباطات ارائه گردد</span></span></span></p>"
							+ "<p dir='rtl' style='box-sizing: border-box; margin: 0px 0px 10px; color: rgb(56, 64, 71); font-family: Tahoma; font-size: 16px; line-height: 20px;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>اعضای پروژه:</span></span></span></p>"
							+ "<ul dir='rtl' style='box-sizing: border-box; margin-top: 0px; margin-bottom: 10px; color: rgb(56, 64, 71); font-family: Tahoma; font-size: 16px; line-height: 20px;'>"
							+ "<li style='box-sizing: border-box;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>پویان ناهد</span></span></span></li>"
							+ "<li style='box-sizing: border-box;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>صدرا صمدی</span></span></span></li>"
							+ "<li style='box-sizing: border-box;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>آرش دادرس</span></span></span></li>"
							+ "<li style='box-sizing: border-box;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>علیرضا تقوی</span></span></span></li>"
							+ "<li style='box-sizing: border-box;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>علیرضا دربندی(super advisor)</span></span></span></li>"
							+ "</ul>"
							+ "<p dir='rtl' style='box-sizing: border-box; margin: 0px 0px 10px; color: rgb(56, 64, 71); font-family: Tahoma; font-size: 16px; line-height: 20px;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>تلفن تماس همراه: ۹۲۱۶۷۲۰۴۹۶+</span></span></span></p>"
							+ "<p dir='rtl' style='box-sizing: border-box; margin: 0px 0px 10px; color: rgb(56, 64, 71); font-family: Tahoma; font-size: 16px; line-height: 20px;'><span style='font-size:10px;'><span style='font-family:tahoma,geneva,sans-serif;'><span style='box-sizing: border-box;'>آدرس ایمیل: info@onchapline.ir</span></span></span></p>"
							+ "</html>");
			label.setHorizontalTextPosition(SwingConstants.LEFT);
			label.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});
				closeButton.setActionCommand("OK");
				buttonPane.add(closeButton);
				getRootPane().setDefaultButton(closeButton);
			}
		}
		pack();
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}

}