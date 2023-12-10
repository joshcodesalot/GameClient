package com.pokeworld;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.pokeworld.constants.Language;
import com.pokeworld.constants.ServerPacket;
import com.pokeworld.protocol.ClientMessage;

/**
 * Generates packets and sends them to the server
 * 
 * @author shadowkanji
 */
public class UserManager
{
	/**
	 * Sends a password change packet
	 * 
	 * @param username
	 * @param newPassword
	 * @param oldPassword
	 * @throws NoSuchAlgorithmException 
	 */
	public void changePassword(String username, String newPassword, String oldPassword) throws NoSuchAlgorithmException
	{
		ClientMessage message = new ClientMessage(ServerPacket.CHANGE_PASSWORD);
		message.addString(username + "," + getMD5PasswordHash(newPassword) + "," + getMD5PasswordHash(oldPassword));
		GameClient.getInstance().getSession().send(message);
	}

	/**
	 * Sends a login packet to server and chat server
	 * 
	 * @param username
	 * @param password
	 * @throws NoSuchAlgorithmException 
	 */
	public void login(String username, String password) throws NoSuchAlgorithmException
	{
		byte language = 0;
		switch(GameClient.getInstance().getLanguage()) {
		case Language.ENGLISH:
			language = 0;
			break;
		case Language.PORTUGESE:
			language = 1;
			break;
		case Language.ITALIAN:
			language = 2;
			break;
		case Language.FRENCH:
			language = 3;
			break;
		case Language.FINNISH:
			language = 4;
			break;
		case Language.SPANISH:
			language = 5;
			break;
		case Language.DUTCH:
			language = 6;
			break;
		case Language.GERMAN:
			language = 7;
			break;
		default:
			language = 0;
			break;
		}

		ClientMessage message = new ClientMessage(ServerPacket.LOGIN);
		message.addString(language + username + "," + getMD5PasswordHash(password));
		GameClient.getInstance().getSession().send(message);
	}

	/**
	 * Sends a registration packet
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param dob
	 * @param starter
	 * @throws NoSuchAlgorithmException 
	 */
	public void register(String username, String password, String email, String dob, int starter, int sprite, int region) throws NoSuchAlgorithmException
	{
		ClientMessage message = new ClientMessage(ServerPacket.REGISTRATION);
		message.addInt(region);
		message.addString(username + "," + getMD5PasswordHash(password) + "," + email + "," + dob + "," + starter + "," + sprite);
		GameClient.getInstance().getSession().send(message);
	}

	/**
	 * Returns the hashed password
	 * 
	 * @param password
	 * @return
	 * @author Akkarin
	 * @throws NoSuchAlgorithmException 
	 */
	private String getMD5PasswordHash(String password) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hashed = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : hashed) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
}
