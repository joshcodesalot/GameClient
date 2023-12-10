package com.pokeworld.ui.frames.battle;

import java.util.HashMap;

import com.pokeworld.ui.components.Image;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;

public class BattleDialog extends Widget
{
	private BattleCanvas canvas;
	private BattleControlFrame control;

	public BattleDialog()
	{
		canvas = new BattleCanvas();
		control = new BattleControlFrame();
		add(canvas);
		add(control);
	}

	public BattleCanvas getCanvas()
	{
		return canvas;
	}

	public BattleControlFrame getControlFrame()
	{
		return control;
	}

	public void useMove(int i)
	{
		control.useMove(i);
	}

	public void showAttack()
	{
		control.showAttack();
	}

	public void disableMoves()
	{
		control.disableMoves();
	}

	public void showPokePane(boolean b)
	{
		control.showPokePane(b);
	}

	public void setWild(boolean m_isWild)
	{
		control.setWild(m_isWild);
	}

	public void enableMoves()
	{
		control.enableMoves();
	}

	public Button getMoveButton(int i)
	{
		return control.getMoveButtons().get(i);
	}

	public Label getPPLabel(int i)
	{
		return control.getPPLabels().get(i);
	}

	public Label getMoveTypeLabel(int i)
	{
		return control.getMoveTypeLabels().get(i);
	}

	public Button getPokeButton(int i)
	{
		return control.getPokeButtons().get(i);
	}

	public Image getPokeStatus(int i)
	{
		return control.getPokeStatus().get(i);
	}

	public Label getPokeInfo(int i)
	{
		return control.getPokeInfo().get(i);
	}

	public HashMap<String, de.matthiasmann.twl.renderer.Image> getStatusIcons()
	{
		return control.getStatusIcons();
	}

	@Override
	public void layout()
	{
		canvas.setPosition(getInnerX(), getInnerY());
		canvas.setSize(257, 144);
		control.setPosition(getInnerX(), getInnerY() + canvas.getHeight());
		control.setSize(259, 200);

		setSize(257 + 259, 144 + 200);
	}

	public void disableAllPokemon()
	{
		for(Button btn : control.getPokeButtons())
		{
			btn.setEnabled(false);
		}
	}
}
