package com.pokeworld.ui.frames;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.pokeworld.GameClient;
import com.pokeworld.backend.Translator;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;

/**
 * Handles the login box
 * 
 * @author Myth1c
 */
public class LoginDialog extends Widget
{
	private Button m_login, m_register, m_back;
	private Label m_userLabel, m_passLabel;
	private EditField m_username, m_password;

	/**
	 * Default constructor
	 */
	public LoginDialog()
	{
		List<String> translated = Translator.translate("_LOGIN");
		setSize(320, 160);
		setPosition(240, 324);

		/* Set up the components */

		m_username = new EditField();
		m_username.setSize(132, 24);
		m_username.setPosition(128, 8);
		m_username.setVisible(true);
		add(m_username);

		m_password = new EditField();
		m_password.setSize(132, 24);
		m_password.setPosition(128, 40);
		m_password.setVisible(true);
		m_password.setPasswordMasking(true);
		add(m_password);

		m_userLabel = new Label(translated.get(5));
		m_userLabel.setVisible(true);
		add(m_userLabel);

		m_passLabel = new Label(translated.get(6));
		m_passLabel.setVisible(true);
		add(m_passLabel);

		m_back = new Button("Back");
		m_back.setCanAcceptKeyboardFocus(false);
		m_back.setSize(64, 32);
		m_back.setVisible(true);
		m_back.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				GameClient.getInstance().returnToServerSelect();
			}
		});

		add(m_back);

		m_login = new Button(translated.get(7));
		m_login.setCanAcceptKeyboardFocus(false);
		m_login.setSize(64, 32);
		m_login.setVisible(true);
		m_login.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				if(m_username.getText() != null && m_password.getText() != null && !m_username.getText().equals("") && !m_password.getText().equals(""))
				{
					try {
						login();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		add(m_login);

		m_register = new Button(translated.get(8));
		m_register.setCanAcceptKeyboardFocus(false);
		m_register.setSize(64, 32);
		m_register.setVisible(true);
		m_register.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				register();
			}
		});
		add(m_register);

		setVisible(false);
	}

	@Override
	public void layout()
	{
		m_userLabel.setPosition(m_username.getX() - m_userLabel.computeTextWidth() - 24, m_username.getY() + 12);
		m_passLabel.setPosition(m_userLabel.getX(), m_password.getY() + 12);
		m_back.setPosition(m_userLabel.getX(), m_password.getY() + m_password.getHeight() + 8);
		m_login.setPosition(m_password.getX(), m_password.getY() + m_password.getHeight() + 8);
		m_register.setPosition(m_login.getX() + m_login.getWidth() + 8, m_login.getY());
	}

	/**
	 * Returns the login button
	 * 
	 * @return
	 */
	public Button getLoginButton()
	{
		return m_login;
	}

	/**
	 * Enter to login
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public void goLogin() throws NoSuchAlgorithmException
	{
		if(m_username.getText() != null && m_password.getText() != null && !m_username.getText().equals("") && !m_password.getText().equals(""))
		{
			login();
		}
	}

	/**
	 * Reloads strings with language selected.
	 */
	public void reloadStrings()
	{
		List<String> translated = Translator.translate("_LOGIN");
		m_userLabel.setText(translated.get(5));
		m_passLabel.setText(translated.get(6));
		m_login.setText(translated.get(7));
		m_register.setText(translated.get(8));
	}

	/**
	 * Sends login information to packet generator to be sent to server
	 * @throws NoSuchAlgorithmException 
	 */
	private void login() throws NoSuchAlgorithmException
	{
		m_login.setEnabled(false);
		// GameClient.getInstance().getLoadingScreen().setVisible(true);
		GameClient.getInstance().getUserManager().login(m_username.getText(), m_password.getText());
	}

	/**
	 * Opens registration window
	 */
	private void register()
	{
		GameClient.getInstance().getGUIPane().getLoginScreen().showRegistration();
	}
}
