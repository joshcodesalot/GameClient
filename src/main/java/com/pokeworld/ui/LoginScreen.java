package com.pokeworld.ui;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.pokeworld.backend.FileLoader;
import com.pokeworld.backend.Translator;
import com.pokeworld.ui.frames.AboutDialog;
import com.pokeworld.ui.frames.LanguageDialog;
import com.pokeworld.ui.frames.LoginDialog;
import com.pokeworld.ui.frames.RegisterDialog;
import com.pokeworld.ui.frames.ServerDialog;
import com.pokeworld.ui.frames.ToSDialog;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DesktopArea;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.Image;

/**
 * The complete login screen
 * 
 * @author Myth1c, Chappie112
 */

public class LoginScreen extends DesktopArea
{
	private LanguageDialog m_languageDialog;
	private AboutDialog m_about;
	private ToSDialog m_terms;
	private LoginDialog m_login;
	private ServerDialog m_select;

	private RegisterDialog m_register;

	//private Label m_serverRev;
	private Button m_openAbout;
	private Button m_openToS;

	public LoginScreen()
	{
		setSize(800, 600);
		setPosition(0, 0);
		
		List<String> translated = new ArrayList<String>();
		translated = Translator.translate("_LOGIN");

		m_languageDialog = new LanguageDialog();
		add(m_languageDialog);

		m_terms = new ToSDialog();
		add(m_terms);

		m_about = new AboutDialog();
		add(m_about);

		m_login = new LoginDialog();
		add(m_login);

		m_select = new ServerDialog();
		add(m_select);

		m_register = new RegisterDialog();
		add(m_register);

		m_openAbout = new Button(translated.get(3));
		m_openAbout.setVisible(true);
		m_openAbout.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				showAbout();
			}
		});
		add(m_openAbout);

		m_openToS = new Button(translated.get(4));
		m_openToS.setVisible(true);
		m_openToS.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				showToS();
			}
		});
		add(m_openToS);

		//m_serverRev = new Label("");
		//m_serverRev.setVisible(false);
		//add(m_serverRev);
	}

	@Override
	public void layout()
	{
		//m_serverRev.setPosition(4, 600 - m_serverRev.getHeight() - 10);
		m_openAbout.setSize(64, 32);
		m_openAbout.setPosition(728, 8);
		m_openToS.setSize(64, 32);
		m_openToS.setPosition(728, 45);
	}

	/**
	 * Sets the server version to be displayed
	 * 
	 * @param rev
	 */
	public void setServerRevision(int rev)
	{
		//m_serverRev.setText("Server Version: r" + rev);
		//invalidateLayout();
	}

	/**
	 * Shows about dialog
	 */
	public void showAbout()
	{
		m_about.reloadStrings();
		m_about.setVisible(false);
	}

	/**
	 * Shows the terms of service dialog
	 */
	public void showToS()
	{
		m_terms.reloadStrings();
		m_terms.setVisible(true);
	}

	/**
	 * Shows the server selection dialog
	 */
	public void showServerSelect()
	{
		m_register.setVisible(false);
		m_login.setVisible(false);
		m_select.reloadStrings();
		m_select.setVisible(true);
		m_languageDialog.setVisible(false);
	}

	/**
	 * Shows the registration dialog
	 */
	public void showRegistration()
	{
		m_select.setVisible(false);
		m_login.setVisible(false);
		m_languageDialog.setVisible(false);
		m_register.reloadStrings();
		m_register.setVisible(true);
	}

	/**
	 * Shows the login dialog
	 */
	public void showLogin()
	{
		m_login.reloadStrings();
		m_select.setVisible(false);
		m_register.setVisible(false);
		m_login.setVisible(true);
		m_login.getLoginButton().setEnabled(true);
		m_languageDialog.setVisible(false);
	}

	/**
	 * Shows the server selection dialogs
	 */
	public void showLanguageSelect()
	{
		m_register.setVisible(false);
		m_login.setVisible(false);
		m_select.setVisible(false);
		m_languageDialog.setVisible(true);
	}

	/**
	 * Logs the user with current user and pass, this way they don't have to click "Login".
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public void enterKeyDefault() throws NoSuchAlgorithmException
	{
		if(!m_languageDialog.isVisible())
		{
			if(m_select.isVisible())
			{
				m_select.goServer();
			}
			else
			{
				m_login.goLogin();
			}
		}
	}

	public void loadBackground(GUI gui)
	{
		String backgroundPath = "res/loginscreen/destiny_standard.png";
		Image i = FileLoader.loadImage(backgroundPath);
		gui.setBackground(i);
	}

	public RegisterDialog getRegistration()
	{
		return m_register;
	}

	/**
	 * Enables the login button
	 */
	public void enableLogin()
	{
		m_login.getLoginButton().setEnabled(true);
	}

	public void hideServerRevision()
	{
	//	m_serverRev.setVisible(false);
	}

	public void showServerRevision()
	{
		//m_serverRev.setVisible(true);
	}
}
