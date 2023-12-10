package com.pokeworld.ui.frames;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pokeworld.GameClient;
import com.pokeworld.backend.FileLoader;
import com.pokeworld.constants.ServerPacket;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.ui.components.Image;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.ListBox.CallbackReason;
import de.matthiasmann.twl.ResizableFrame;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleChangableListModel;

public class SpriteChooserDialog extends ResizableFrame
{
	protected Image m_spriteDisplay;
	protected ListBox<String> m_spriteList;
	private SimpleChangableListModel<String> spritelistmodel;
	private String m_respath;
	private List<String> m_sprites;
	private Widget pane;
	private Button use;
	private Button cancel;

	public SpriteChooserDialog()
	{
		setTheme("chooserDialog");
		pane = new Widget();
		pane.setTheme("content");
		pane.setSize(265, 340);
		pane.setPosition(0, 0);

		m_sprites = new ArrayList<String>();
		m_respath = System.getProperty("res.path");
		if(m_respath == null)
			m_respath = "";
		for(int i = 1; i <= 384; i++)
			m_sprites.add(String.valueOf(i));
		/* Handle blocked sprites */
		InputStream in = null;
		in = FileLoader.loadFile(m_respath + "res/characters/sprites.txt");
		Scanner s = new Scanner(in);
		while(s.hasNextLine())
		{
			m_sprites.remove(s.nextLine());
		}
		s.close();
		m_spriteDisplay = new Image();
		pane.add(m_spriteDisplay);
		spritelistmodel = new SimpleChangableListModel<>();
		for(String sp : m_sprites)
		{
			spritelistmodel.addElement(sp);
		}
		m_spriteList = new ListBox<String>(spritelistmodel);
		m_spriteList.addCallback(new CallbackWithReason<ListBox.CallbackReason>()
		{
			@Override
			public void callback(CallbackReason arg0)
			{
				if(arg0 == CallbackReason.KEYBOARD || arg0 == CallbackReason.MOUSE_CLICK)
				{
					loadSprite(m_respath + "res/characters/" + spritelistmodel.getEntry(m_spriteList.getSelected()) + ".png");
				}
			}
		});
		pane.add(m_spriteList);
		setTitle("Please choose your character..");
		setSize(265, 340);
		setResizableAxis(ResizableAxis.NONE);
		setDraggable(true);
		setVisible(true);
		initUse();
		add(pane);
	}

	protected void loadSprite(String spriteToLoad)
	{
		m_spriteDisplay.setImage(FileLoader.loadImage(spriteToLoad));
	}

	public int getChoice()
	{
		return m_spriteList.getSelected();
	}

	public void initUse()
	{
		use = new Button("Use new sprite!");
		use.setCanAcceptKeyboardFocus(false);
		pane.add(use);
		cancel = new Button("Cancel");
		cancel.setCanAcceptKeyboardFocus(false);
		pane.add(cancel);
		cancel.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				GameClient.getInstance().getHUD().removeSpritechooser();
			}
		});
		use.addCallback(new Runnable()
		{
			@Override
			public void run()
			{
				GameClient.getInstance().getHUD().removeSpritechooser();
				Runnable yes = new Runnable()
				{
					@Override
					public void run()
					{
						GameClient.getInstance().getGUIPane().hideConfirmationDialog();
						ClientMessage message = new ClientMessage(ServerPacket.BUY_SPRITE);
						message.addInt(Integer.parseInt(spritelistmodel.getEntry(m_spriteList.getSelected())));
						GameClient.getInstance().getSession().send(message);
					}
				};
				Runnable no = new Runnable()
				{
					@Override
					public void run()
					{
						GameClient.getInstance().getGUIPane().hideConfirmationDialog();
					}
				};
				GameClient.getInstance().getGUIPane().showConfirmationDialog("Are you sure you want to change sprites?\nIt'll cost you P500!", yes, no);
			}
		});
	}

	@Override
	public void layout()
	{
		super.layout();
		use.setSize(115, 20);
		use.setPosition(getInnerX() + 130, getInnerY() + 245);
		cancel.setPosition(getInnerX() + 145, getInnerY() + 280);
		cancel.setSize(80, 20);
		m_spriteList.setPosition(getInnerX() + 0, getInnerY() + 23);
		m_spriteList.setSize(105, 317);
		m_spriteDisplay.setSize(124, 204);
		m_spriteDisplay.setPosition(getInnerX() + 125, getInnerY() + 40);
	}
}