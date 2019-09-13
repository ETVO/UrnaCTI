/*
 *  JDateChooser.java  - A bean for choosing a date
 *  Copyright (C) 2004 Kai Toedter
 *  kai@toedter.com
 *  www.toedter.com
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

/************************************************************************************************************************************************************
 *  A utility class for some date operations.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 95 $
 * @version $LastChangedDate: 2006-05-05 18:43:15 +0200 (Fr, 05 Mai 2006) $
 */
class DateUtil {
	protected Date minSelectableDate;

	protected Date maxSelectableDate;

	protected Date defaultMinSelectableDate;

	protected Date defaultMaxSelectableDate;

	public DateUtil() {
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.set(1, 0, 1, 1, 1);
		defaultMinSelectableDate = tmpCalendar.getTime();
		minSelectableDate = defaultMinSelectableDate;
		tmpCalendar.set(9999, 0, 1, 1, 1);
		defaultMaxSelectableDate = tmpCalendar.getTime();
		maxSelectableDate = defaultMaxSelectableDate;
	}

	/**
	 * Sets a valid date range for selectable dates. If max is before min, the
	 * default range with no limitation is set.
	 * 
	 * @param min
	 *            the minimum selectable date or null (then the minimum date is
	 *            set to 01\01\0001)
	 * @param max
	 *            the maximum selectable date or null (then the maximum date is
	 *            set to 01\01\9999)
	 */
	public void setSelectableDateRange(Date min, Date max) {
		if (min == null) {
			minSelectableDate = defaultMinSelectableDate;
		} else {
			minSelectableDate = min;
		}
		if (max == null) {
			maxSelectableDate = defaultMaxSelectableDate;
		} else {
			maxSelectableDate = max;
		}
		if (maxSelectableDate.before(minSelectableDate)) {
			minSelectableDate = defaultMinSelectableDate;
			maxSelectableDate = defaultMaxSelectableDate;
		}
	}

	/**
	 * Sets the maximum selectable date. If null, the date 01\01\9999 will be set instead.
	 * 
	 * @param max the maximum selectable date
	 * 
	 * @return the maximum selectable date
	 */
	public Date setMaxSelectableDate(Date max) {
		if (max == null) {
			maxSelectableDate = defaultMaxSelectableDate;
		} else {
			maxSelectableDate = max;
		}
		return maxSelectableDate;
	}

	/**
	 * Sets the minimum selectable date. If null, the date 01\01\0001 will be set instead.
	 * 
	 * @param min the minimum selectable date
	 * 
	 * @return the minimum selectable date
	 */
	public Date setMinSelectableDate(Date min) {
		if (min == null) {
			minSelectableDate = defaultMinSelectableDate;
		} else {
			minSelectableDate = min;
		}
		return minSelectableDate;
	}

	/**
	 * Gets the maximum selectable date.
	 * 
	 * @return the maximum selectable date
	 */
	public Date getMaxSelectableDate() {
		return maxSelectableDate;
	}

	/**
	 * Gets the minimum selectable date.
	 * 
	 * @return the minimum selectable date
	 */
	public Date getMinSelectableDate() {
		return minSelectableDate;
	}

	/**
	 * Checks a given date if it is in the formally specified date range.
	 * 
	 * @param date
	 *            the date to check
	 * @return true, if the date is within minSelectableDate and
	 *         maxSelectableDate
	 */
	public boolean checkDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Calendar minCal = Calendar.getInstance();
		minCal.setTime(minSelectableDate);
		minCal.set(Calendar.HOUR_OF_DAY, 0);
		minCal.set(Calendar.MINUTE, 0);
		minCal.set(Calendar.SECOND, 0);
		minCal.set(Calendar.MILLISECOND, 0);

		Calendar maxCal = Calendar.getInstance();
		maxCal.setTime(maxSelectableDate);
		maxCal.set(Calendar.HOUR_OF_DAY, 0);
		maxCal.set(Calendar.MINUTE, 0);
		maxCal.set(Calendar.SECOND, 0);
		maxCal.set(Calendar.MILLISECOND, 0);
		
		return !(calendar.before(minCal) || calendar.after(maxCal));
	}

}

/***********************************************************************************************************************************************************************
 * Implementations of this interface can be added to various JCalendar
 * components to check if certain dates are valid for selection.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 142 $
 * @version $LastChangedDate: 2011-06-05 07:06:03 +0200 (So, 05 Jun 2011) $
 * 
 */
interface IDateEvaluator {
	/**
	 * Checks if a date is a special date (might have different colors and tooltips)
	 * 
	 * @param date
	 *            the date to check
	 * @return true, if the date can be selected
	 */
	boolean isSpecial(Date date);

	/**
	 * @return the foreground color (used by JDayChooser)
	 */
	Color getSpecialForegroundColor();

	/**
	 * @return the background color (used by JDayChooser)
	 */
	Color getSpecialBackroundColor();
	
	/**
	 * @return the tooltip (used by JDayChooser)
	 */
	String getSpecialTooltip();
	
	/**
	 * Checks if a date is invalid for selection
	 * 
	 * @param date
	 *            the date to check
	 * @return true, if the date is invalid and cannot be selected
	 */
	boolean isInvalid(Date date);

	/**
	 * @return the foreground color (used by JDayChooser)
	 */
	Color getInvalidForegroundColor();

	/**
	 * @return the background color (used by JDayChooser)
	 */
	Color getInvalidBackroundColor();
	
	/**
	 * @return the tooltip (used by JDayChooser)
	 */
	String getInvalidTooltip();

}

class MinMaxDateEvaluator implements IDateEvaluator {

	private DateUtil dateUtil = new DateUtil();
	
	public boolean isSpecial(Date date) {
		return false;
	}

	public Color getSpecialForegroundColor() {
		return null;
	}

	public Color getSpecialBackroundColor() {
		return null;
	}

	public String getSpecialTooltip() {
		return null;
	}

	public boolean isInvalid(Date date) {
		return !dateUtil.checkDate(date);
	}

	public Color getInvalidForegroundColor() {
		return null;
	}

	public Color getInvalidBackroundColor() {
		return null;
	}

	public String getInvalidTooltip() {
		return null;
	}

	/**
	 * Sets the maximum selectable date. If null, the date 01\01\9999 will be
	 * set instead.
	 * 
	 * @param max
	 *            the maximum selectable date
	 * 
	 * @return the maximum selectable date
	 */
	public Date setMaxSelectableDate(Date max) {
		return dateUtil.setMaxSelectableDate(max);
	}

	/**
	 * Sets the minimum selectable date. If null, the date 01\01\0001 will be
	 * set instead.
	 * 
	 * @param min
	 *            the minimum selectable date
	 * 
	 * @return the minimum selectable date
	 */
	public Date setMinSelectableDate(Date min) {
		return dateUtil.setMinSelectableDate(min);
	}

	/**
	 * Gets the maximum selectable date.
	 * 
	 * @return the maximum selectable date
	 */
	public Date getMaxSelectableDate() {
		return dateUtil.getMaxSelectableDate();
	}

	/**
	 * Gets the minimum selectable date.
	 * 
	 * @return the minimum selectable date
	 */
	public Date getMinSelectableDate() {
		return dateUtil.getMinSelectableDate();
	}

}
/***************************************************************************************************************************************************************************************************************************
 * JSpinField is a numeric field with 2 spin buttons to increase or decrease the
 * value. It has the same interface as the "old" JSpinField but uses a JSpinner
 * internally (since J2SE SDK 1.4) rather than a scrollbar for emulating the
 * spin buttons.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 85 $
 * @version $LastChangedDate: 2006-04-28 13:50:52 +0200 (Fr, 28 Apr 2006) $
 */
class JSpinField extends JPanel implements ChangeListener, CaretListener, ActionListener, FocusListener
{
	protected JSpinner spinner;

	/** the text (number) field */
	protected JTextField textField;
	protected int min;
	protected int max;
	protected int value;
	protected Color darkGreen;

	/**
	 * Default JSpinField constructor. The valid value range is between
	 * Integer.MIN_VALUE and Integer.MAX_VALUE. The initial value is 0.
	 */
	public JSpinField() {
		this(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * JSpinField constructor with given minimum and maximum vaues and initial
	 * value 0.
	 */
	public JSpinField(int min, int max) {
		super();
		setName("JSpinField");
		this.min = min;
		if (max < min)
			max = min;
		this.max = max;
		value = 0;
		if (value < min)
			value = min;
		if (value > max)
			value = max;

		darkGreen = new Color(0, 150, 0);
		setLayout(new BorderLayout());
		textField = new JTextField();
		textField.addCaretListener(this);
		textField.addActionListener(this);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setText(Integer.toString(value));
		textField.addFocusListener(this);
		spinner = new JSpinner() {
			private static final long serialVersionUID = -6287709243342021172L;
			private JTextField textField = new JTextField();

			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				return new Dimension(size.width, textField.getPreferredSize().height);
			}
		};
		spinner.setEditor(textField);
		spinner.addChangeListener(this);
		// spinner.setSize(spinner.getWidth(), textField.getHeight());
		add(spinner, BorderLayout.CENTER);
	}

	public void adjustWidthToMaximumValue() {
		JTextField testTextField = new JTextField(Integer.toString(max));
		int width = testTextField.getPreferredSize().width;
		int height = testTextField.getPreferredSize().height;
		textField.setPreferredSize(new Dimension(width, height));
		textField.revalidate();
	}

	/**
	 * Is invoked when the spinner model changes
	 * 
	 * @param e
	 *            the ChangeEvent
	 */
	public void stateChanged(ChangeEvent e) {
		SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
		int value = model.getNumber().intValue();
		setValue(value);
	}

	/**
	 * Sets the value attribute of the JSpinField object.
	 * 
	 * @param newValue
	 *            The new value
	 * @param updateTextField
	 *            true if text field should be updated
	 */
	protected void setValue(int newValue, boolean updateTextField, boolean firePropertyChange) {
		int oldValue = value;
		if (newValue < min) {
			value = min;
		} else if (newValue > max) {
			value = max;
		} else {
			value = newValue;
		}

		if (updateTextField) {
			textField.setText(Integer.toString(value));
			textField.setForeground(Color.black);
		}

		if (firePropertyChange) {
			firePropertyChange("value", oldValue, value);
		}
	}

	/**
	 * Sets the value. This is a bound property.
	 * 
	 * @param newValue
	 *            the new value
	 * 
	 * @see #getValue
	 */
	public void setValue(int newValue) {
		setValue(newValue, true, true);
		spinner.setValue(new Integer(value));
	}

	/**
	 * Returns the value.
	 * 
	 * @return the value value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the minimum value.
	 * 
	 * @param newMinimum
	 *            the new minimum value
	 * 
	 * @see #getMinimum
	 */
	public void setMinimum(int newMinimum) {
		min = newMinimum;
	}

	/**
	 * Returns the minimum value.
	 * 
	 * @return the minimum value
	 */
	public int getMinimum() {
		return min;
	}

	/**
	 * Sets the maximum value and adjusts the preferred width.
	 * 
	 * @param newMaximum
	 *            the new maximum value
	 * 
	 * @see #getMaximum
	 */
	public void setMaximum(int newMaximum) {
		max = newMaximum;
	}

	/**
	 * Sets the horizontal alignment of the displayed value.
	 * 
	 * @param alignment
	 *            the horizontal alignment
	 */
	public void setHorizontalAlignment(int alignment) {
		textField.setHorizontalAlignment(alignment);
	}

	/**
	 * Returns the maximum value.
	 * 
	 * @return the maximum value
	 */
	public int getMaximum() {
		return max;
	}

	/**
	 * Sets the font property.
	 * 
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		if (textField != null) {
			textField.setFont(font);
		}
	}

	/**
	 * Sets the foreground
	 * 
	 * @param fg
	 *            the foreground
	 */
	public void setForeground(Color fg) {
		if (textField != null) {
			textField.setForeground(fg);
		}
	}

	/**
	 * After any user input, the value of the textfield is proofed. Depending on
	 * being an integer, the value is colored green or red.
	 * 
	 * @param e
	 *            the caret event
	 */
	public void caretUpdate(CaretEvent e) {
		try {
			int testValue = Integer.valueOf(textField.getText()).intValue();

			if ((testValue >= min) && (testValue <= max)) {
				textField.setForeground(darkGreen);
				setValue(testValue, false, true);
			} else {
				textField.setForeground(Color.red);
			}
		} catch (Exception ex) {
			if (ex instanceof NumberFormatException) {
				textField.setForeground(Color.red);
			}

			// Ignore all other exceptions, e.g. illegal state exception
		}

		textField.repaint();
	}

	/**
	 * After any user input, the value of the textfield is proofed. Depending on
	 * being an integer, the value is colored green or red. If the textfield is
	 * green, the enter key is accepted and the new value is set.
	 * 
	 * @param e
	 *            Description of the Parameter
	 */
	public void actionPerformed(ActionEvent e) {
		if (textField.getForeground().equals(darkGreen)) {
			setValue(Integer.valueOf(textField.getText()).intValue());
		}
	}

	/**
	 * Enable or disable the JSpinField.
	 * 
	 * @param enabled
	 *            The new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		spinner.setEnabled(enabled);
		textField.setEnabled(enabled);
		/*
		 * Fixes the background bug
		 * 4991597 and sets the background explicitely to a
		 * TextField.inactiveBackground.
		 */
		if (!enabled) {
			textField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		}
	}

	/**
	 * Returns the year chooser's spinner (which allow the focus to be set to
	 * it).
	 * 
	 * @return Component the spinner or null, if the month chooser has no
	 *         spinner
	 */
	public Component getSpinner() {
		return spinner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
	}

	/**
	 * The value of the text field is checked against a valid (green) value. If
	 * valid, the value is set and a property change is fired.
	 */
	public void focusLost(FocusEvent e) {
		actionPerformed(null);
	}
}



/**
 * JYearChooser is a bean for choosing a year.
 *
 * @version $LastChangedRevision: 85 $
 * @version $LastChangedDate: 2006-04-28 13:50:52 +0200 (Fr, 28 Apr 2006) $
 */
class JYearChooser extends JSpinField {
	
	protected JDayChooser dayChooser;
    protected int oldYear;
    protected int startYear;
    protected int endYear;

    /**
     * Default JCalendar constructor.
     */
    public JYearChooser() {
        setName("JYearChooser");
        Calendar calendar = Calendar.getInstance();
        dayChooser = null;
        setMinimum(calendar.getMinimum(Calendar.YEAR));
        setMaximum(calendar.getMaximum(Calendar.YEAR));
        setValue(calendar.get(Calendar.YEAR));
    }

    /**
     * Sets the year. This is a bound property.
     *
     * @param y the new year
     *
     * @see #getYear
     */
    public void setYear(int y) {
        super.setValue(y, true, false);

        if (dayChooser != null) {
            dayChooser.setYear(value);
        }

        spinner.setValue(new Integer(value));
        firePropertyChange("year", oldYear, value);
        oldYear = value;
    }

    /**
     * Sets the year value.
     *
     * @param value the year value
     */
    public void setValue(int value) {
        setYear(value);
    }

    /**
     * Returns the year.
     *
     * @return the year
     */
    public int getYear() {
        return super.getValue();
    }

    /**
     * Convenience method set a day chooser that might be updated directly.
     *
     * @param dayChooser the day chooser
     */
    public void setDayChooser(JDayChooser dayChooser) {
        this.dayChooser = dayChooser;
    }

    /**
     * Returns the endy ear.
     *
     * @return the end year
     */
    public int getEndYear() {
        return getMaximum();
    }

    /**
     * Sets the end ear.
     *
     * @param endYear the end year
     */
    public void setEndYear(int endYear) {
        setMaximum(endYear);
    }

    /**
     * Returns the start year.
     *
     * @return the start year.
     */
    public int getStartYear() {
        return getMinimum();
    }

    /**
     * Sets the start year.
     *
     * @param startYear the start year
     */
    public void setStartYear(int startYear) {
        setMinimum(startYear);
    }

    /**
     * Creates a JFrame with a JYearChooser inside and can be used for testing.
     *
     * @param s command line arguments
     
    static public void main(String[] s) {
        JFrame frame = new JFrame("JYearChooser");
        frame.getContentPane().add(new JYearChooser());
        frame.pack();
        frame.setVisible(true);
    }
    */
}

/**
 * JDayChooser is a bean for choosing a day.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 147 $
 * @version $LastChangedDate: 2011-06-06 20:36:53 +0200 (Mo, 06 Jun 2011) $
 */
class JDayChooser extends JPanel implements ActionListener, KeyListener,
		FocusListener {
	private static final long serialVersionUID = 5876398337018781820L;

	protected JButton[] days;

	protected JButton[] weeks;

	protected JButton selectedDay;

	protected JPanel weekPanel;

	protected JPanel dayPanel;

	protected int day;

	protected Color oldDayBackgroundColor;

	protected Color selectedColor;

	protected Color sundayForeground;

	protected Color weekdayForeground;

	protected Color decorationBackgroundColor;

	protected String[] dayNames;

	protected Calendar calendar;

	protected Calendar today;

	protected Locale locale;

	protected boolean initialized;

	protected boolean weekOfYearVisible;

	protected boolean decorationBackgroundVisible = true;

	protected boolean decorationBordersVisible;

	protected boolean dayBordersVisible;

	private boolean alwaysFireDayProperty;

	protected int maxDayCharacters;

	protected List dateEvaluators;
	
	protected MinMaxDateEvaluator minMaxDateEvaluator;

	/**
	 * Default JDayChooser constructor.
	 */
	public JDayChooser() {
		this(false);
	}

	/**
	 * JDayChooser constructor.
	 * 
	 * @param weekOfYearVisible
	 *            true, if the weeks of a year shall be shown
	 */
	public JDayChooser(boolean weekOfYearVisible) {
		setName("JDayChooser");
		setBackground(Color.blue);

		dateEvaluators = new ArrayList(1);
		minMaxDateEvaluator = new MinMaxDateEvaluator();
		addDateEvaluator(minMaxDateEvaluator);

		this.weekOfYearVisible = weekOfYearVisible;
		locale = Locale.getDefault();
		days = new JButton[49];
		selectedDay = null;
		calendar = Calendar.getInstance(locale);
		today = (Calendar) calendar.clone();

		setLayout(new BorderLayout());

		dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(7, 7));

		sundayForeground = new Color(164, 0, 0);
		weekdayForeground = new Color(0, 90, 164);

		// decorationBackgroundColor = new Color(194, 211, 252);
		// decorationBackgroundColor = new Color(206, 219, 246);
		decorationBackgroundColor = new Color(210, 228, 238);

		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				int index = x + (7 * y);

				if (y == 0) {
					// Create a button that doesn't react on clicks or focus
					// changes.
					// Thanks to Thomas Schaefer for the focus hint :)
					days[index] = new DecoratorButton();
				} else {
					days[index] = new JButton("x") {
						private static final long serialVersionUID = -7433645992591669725L;

						public void paint(Graphics g) {
							if ("Windows".equals(UIManager.getLookAndFeel()
									.getID())) {
								// this is a hack to get the background painted
								// when using Windows Look & Feel
								if (selectedDay == this) {
									g.setColor(selectedColor);
									g.fillRect(0, 0, getWidth(), getHeight());
								}
							}
							super.paint(g);
						}

					};
					days[index].addActionListener(this);
					days[index].addKeyListener(this);
					days[index].addFocusListener(this);
				}

				days[index].setMargin(new Insets(0, 0, 0, 0));
				days[index].setFocusPainted(false);
				dayPanel.add(days[index]);
			}
		}

		weekPanel = new JPanel();
		weekPanel.setLayout(new GridLayout(7, 1));
		weeks = new JButton[7];

		for (int i = 0; i < 7; i++) {
			weeks[i] = new DecoratorButton();
			weeks[i].setMargin(new Insets(0, 0, 0, 0));
			weeks[i].setFocusPainted(false);
			weeks[i].setForeground(new Color(100, 100, 100));

			if (i != 0) {
				weeks[i].setText("0" + (i + 1));
			}

			weekPanel.add(weeks[i]);
		}

		init();

		setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		add(dayPanel, BorderLayout.CENTER);

		if (weekOfYearVisible) {
			add(weekPanel, BorderLayout.WEST);
		}

		initialized = true;
		updateUI();
	}

	/**
	 * Initializes the locale specific names for the days of the week.
	 */
	protected void init() {
		JButton testButton = new JButton();
		oldDayBackgroundColor = testButton.getBackground();
		selectedColor = new Color(160, 160, 160);

		Date date = calendar.getTime();
		calendar = Calendar.getInstance(locale);
		calendar.setTime(date);

		drawDayNames();
		drawDays();
	}

	/**
	 * Draws the day names of the day columnes.
	 */
	private void drawDayNames() {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		dayNames = dateFormatSymbols.getShortWeekdays();

		int day = firstDayOfWeek;

		for (int i = 0; i < 7; i++) {
			if (maxDayCharacters > 0 && maxDayCharacters < 5) {
				if (dayNames[day].length() >= maxDayCharacters) {
					dayNames[day] = dayNames[day]
							.substring(0, maxDayCharacters);
				}
			}

			days[i].setText(dayNames[day]);

			if (day == 1) {
				days[i].setForeground(sundayForeground);
			} else {
				days[i].setForeground(weekdayForeground);
			}

			if (day < 7) {
				day++;
			} else {
				day -= 6;
			}
		}
	}

	/**
	 * Initializes both day names and weeks of the year.
	 */
	protected void initDecorations() {
		for (int x = 0; x < 7; x++) {
			days[x].setContentAreaFilled(decorationBackgroundVisible);
			days[x].setBorderPainted(decorationBordersVisible);
			days[x].invalidate();
			days[x].repaint();
			weeks[x].setContentAreaFilled(decorationBackgroundVisible);
			weeks[x].setBorderPainted(decorationBordersVisible);
			weeks[x].invalidate();
			weeks[x].repaint();
		}
	}

	/**
	 * Hides and shows the week buttons.
	 */
	protected void drawWeeks() {
		Calendar tmpCalendar = (Calendar) calendar.clone();

		for (int i = 1; i < 7; i++) {
			tmpCalendar.set(Calendar.DAY_OF_MONTH, (i * 7) - 6);

			int week = tmpCalendar.get(Calendar.WEEK_OF_YEAR);
			String buttonText = Integer.toString(week);

			if (week < 10) {
				buttonText = "0" + buttonText;
			}

			weeks[i].setText(buttonText);

			if ((i == 5) || (i == 6)) {
				weeks[i].setVisible(days[i * 7].isVisible());
			}
		}
	}

	/**
	 * Hides and shows the day buttons.
	 */
	protected void drawDays() {
		Calendar tmpCalendar = (Calendar) calendar.clone();
		tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
		tmpCalendar.set(Calendar.MINUTE, 0);
		tmpCalendar.set(Calendar.SECOND, 0);
		tmpCalendar.set(Calendar.MILLISECOND, 0);

		int firstDayOfWeek = tmpCalendar.getFirstDayOfWeek();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);

		int firstDay = tmpCalendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;

		if (firstDay < 0) {
			firstDay += 7;
		}

		int i;

		for (i = 0; i < firstDay; i++) {
			days[i + 7].setVisible(false);
			days[i + 7].setText("");
		}

		tmpCalendar.add(Calendar.MONTH, 1);

		Date firstDayInNextMonth = tmpCalendar.getTime();
		tmpCalendar.add(Calendar.MONTH, -1);

		Date day = tmpCalendar.getTime();
		int n = 0;
		Color foregroundColor = getForeground();

		while (day.before(firstDayInNextMonth)) {
			days[i + n + 7].setText(Integer.toString(n + 1));
			days[i + n + 7].setVisible(true);

			if ((tmpCalendar.get(Calendar.DAY_OF_YEAR) == today
					.get(Calendar.DAY_OF_YEAR))
					&& (tmpCalendar.get(Calendar.YEAR) == today
							.get(Calendar.YEAR))) {
				days[i + n + 7].setForeground(sundayForeground);
			} else {
				days[i + n + 7].setForeground(foregroundColor);
			}

			if ((n + 1) == this.day) {
				days[i + n + 7].setBackground(selectedColor);
				selectedDay = days[i + n + 7];
			} else {
				days[i + n + 7].setBackground(oldDayBackgroundColor);
			}

			Iterator iterator = dateEvaluators.iterator(); 
			days[i + n + 7].setEnabled(true);
			while (iterator.hasNext()) {
				IDateEvaluator dateEvaluator = (IDateEvaluator) iterator.next();
				if (dateEvaluator.isSpecial(day)) {
					days[i + n + 7].setForeground(dateEvaluator
							.getSpecialForegroundColor());
					days[i + n + 7].setBackground(dateEvaluator
							.getSpecialBackroundColor());
					days[i + n + 7].setToolTipText(dateEvaluator.getSpecialTooltip());
					days[i + n + 7].setEnabled(true);
				} 
				if (dateEvaluator.isInvalid(day)){
					days[i + n + 7].setForeground(dateEvaluator
							.getInvalidForegroundColor());
					days[i + n + 7].setBackground(dateEvaluator
							.getInvalidBackroundColor());
					days[i + n + 7].setToolTipText(dateEvaluator.getInvalidTooltip());
					days[i + n + 7].setEnabled(false);
				}
			}

			n++;
			tmpCalendar.add(Calendar.DATE, 1);
			day = tmpCalendar.getTime();
		}

		for (int k = n + i + 7; k < 49; k++) {
			days[k].setVisible(false);
			days[k].setText("");
		}

		drawWeeks();
	}

	/**
	 * Returns the locale.
	 * 
	 * @return the locale value
	 * 
	 * @see #setLocale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the locale.
	 * 
	 * @param locale
	 *            the new locale value
	 * 
	 * @see #getLocale
	 */
	public void setLocale(Locale locale) {
		if (!initialized) {
			super.setLocale(locale);
		} else {
			this.locale = locale;
			super.setLocale(locale);
			init();
		}
	}

	/**
	 * Sets the day. This is a bound property.
	 * 
	 * @param d
	 *            the day
	 * 
	 * @see #getDay
	 */
	public void setDay(int d) {
		if (d < 1) {
			d = 1;
		}
		Calendar tmpCalendar = (Calendar) calendar.clone();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
		tmpCalendar.add(Calendar.MONTH, 1);
		tmpCalendar.add(Calendar.DATE, -1);

		int maxDaysInMonth = tmpCalendar.get(Calendar.DATE);

		if (d > maxDaysInMonth) {
			d = maxDaysInMonth;
		}

		int oldDay = day;
		day = d;

		if (selectedDay != null) {
			selectedDay.setBackground(oldDayBackgroundColor);
			selectedDay.repaint();
		}

		for (int i = 7; i < 49; i++) {
			if (days[i].getText().equals(Integer.toString(day))) {
				selectedDay = days[i];
				selectedDay.setBackground(selectedColor);
				break;
			}
		}

		if (alwaysFireDayProperty) {
			firePropertyChange("day", 0, day);
		} else {
			firePropertyChange("day", oldDay, day);
		}
	}

	/**
	 * this is needed for JDateChooser.
	 * 
	 * @param alwaysFire
	 *            true, if day property shall be fired every time a day is
	 *            chosen.
	 */
	public void setAlwaysFireDayProperty(boolean alwaysFire) {
		alwaysFireDayProperty = alwaysFire;
	}

	/**
	 * Returns the selected day.
	 * 
	 * @return the day value
	 * 
	 * @see #setDay
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets a specific month. This is needed for correct graphical
	 * representation of the days.
	 * 
	 * @param month
	 *            the new month
	 */
	public void setMonth(int month) {
		calendar.set(Calendar.MONTH, month);
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (day > maxDays) {
			day = maxDays;
		}

		drawDays();
	}

	/**
	 * Sets a specific year. This is needed for correct graphical representation
	 * of the days.
	 * 
	 * @param year
	 *            the new year
	 */
	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
		drawDays();
	}

	/**
	 * Sets a specific calendar. This is needed for correct graphical
	 * representation of the days.
	 * 
	 * @param calendar
	 *            the new calendar
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		drawDays();
	}

	/**
	 * Sets the font property.
	 * 
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		if (days != null) {
			for (int i = 0; i < 49; i++) {
				days[i].setFont(font);
			}
		}
		if (weeks != null) {
			for (int i = 0; i < 7; i++) {
				weeks[i].setFont(font);
			}
		}
	}

	/**
	 * Sets the foregroundColor color.
	 * 
	 * @param foreground
	 *            the new foregroundColor
	 */
	public void setForeground(Color foreground) {
		super.setForeground(foreground);

		if (days != null) {
			for (int i = 7; i < 49; i++) {
				days[i].setForeground(foreground);
			}

			drawDays();
		}
	}

	/**
	 * JDayChooser is the ActionListener for all day buttons.
	 * 
	 * @param e
	 *            the ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String buttonText = button.getText();
		int day = new Integer(buttonText).intValue();
		setDay(day);
	}

	/**
	 * JDayChooser is the FocusListener for all day buttons. (Added by Thomas
	 * Schaefer)
	 * 
	 * @param e
	 *            the FocusEvent
	 */
	/*
	 * Code below commented out by Mark Brown on 24 Aug 2004. This code breaks
	 * the JDateChooser code by triggering the actionPerformed method on the
	 * next day button. This causes the date chosen to always be incremented by
	 * one day.
	 */
	public void focusGained(FocusEvent e) {
		// JButton button = (JButton) e.getSource();
		// String buttonText = button.getText();
		//
		// if ((buttonText != null) && !buttonText.equals("") &&
		// !e.isTemporary()) {
		// actionPerformed(new ActionEvent(e.getSource(), 0, null));
		// }
	}

	/**
	 * Does nothing.
	 * 
	 * @param e
	 *            the FocusEvent
	 */
	public void focusLost(FocusEvent e) {
	}

	/**
	 * JDayChooser is the KeyListener for all day buttons. (Added by Thomas
	 * Schaefer and modified by Austin Moore)
	 * 
	 * @param e
	 *            the KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		int offset = (e.getKeyCode() == KeyEvent.VK_UP) ? (-7) : ((e
				.getKeyCode() == KeyEvent.VK_DOWN) ? (+7)
				: ((e.getKeyCode() == KeyEvent.VK_LEFT) ? (-1) : ((e
						.getKeyCode() == KeyEvent.VK_RIGHT) ? (+1) : 0)));

		int newDay = getDay() + offset;

		if ((newDay >= 1)
				&& (newDay <= calendar.getMaximum(Calendar.DAY_OF_MONTH))) {
			setDay(newDay);
		}
	}

	/**
	 * Does nothing.
	 * 
	 * @param e
	 *            the KeyEvent
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Does nothing.
	 * 
	 * @param e
	 *            the KeyEvent
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Enable or disable the JDayChooser.
	 * 
	 * @param enabled
	 *            The new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		for (short i = 0; i < days.length; i++) {
			if (days[i] != null) {
				days[i].setEnabled(enabled);
			}
		}

		for (short i = 0; i < weeks.length; i++) {
			if (weeks[i] != null) {
				weeks[i].setEnabled(enabled);
			}
		}
	}

	/**
	 * In some Countries it is often usefull to know in which week of the year a
	 * date is.
	 * 
	 * @return boolean true, if the weeks of the year is shown
	 */
	public boolean isWeekOfYearVisible() {
		return weekOfYearVisible;
	}

	/**
	 * In some Countries it is often usefull to know in which week of the year a
	 * date is.
	 * 
	 * @param weekOfYearVisible
	 *            true, if the weeks of the year shall be shown
	 */
	public void setWeekOfYearVisible(boolean weekOfYearVisible) {
		if (weekOfYearVisible == this.weekOfYearVisible) {
			return;
		} else if (weekOfYearVisible) {
			add(weekPanel, BorderLayout.WEST);
		} else {
			remove(weekPanel);
		}

		this.weekOfYearVisible = weekOfYearVisible;
		validate();
		dayPanel.validate();
	}

	/**
	 * Returns the day panel.
	 * 
	 * @return the day panel
	 */
	public JPanel getDayPanel() {
		return dayPanel;
	}

	/**
	 * Returns the color of the decoration (day names and weeks).
	 * 
	 * @return the color of the decoration (day names and weeks).
	 */
	public Color getDecorationBackgroundColor() {
		return decorationBackgroundColor;
	}

	/**
	 * Sets the background of days and weeks of year buttons.
	 * 
	 * @param decorationBackgroundColor
	 *            The background to set
	 */
	public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
		this.decorationBackgroundColor = decorationBackgroundColor;

		if (days != null) {
			for (int i = 0; i < 7; i++) {
				days[i].setBackground(decorationBackgroundColor);
			}
		}

		if (weeks != null) {
			for (int i = 0; i < 7; i++) {
				weeks[i].setBackground(decorationBackgroundColor);
			}
		}
	}

	/**
	 * Returns the Sunday foreground.
	 * 
	 * @return Color the Sunday foreground.
	 */
	public Color getSundayForeground() {
		return sundayForeground;
	}

	/**
	 * Returns the weekday foreground.
	 * 
	 * @return Color the weekday foreground.
	 */
	public Color getWeekdayForeground() {
		return weekdayForeground;
	}

	/**
	 * Sets the Sunday foreground.
	 * 
	 * @param sundayForeground
	 *            The sundayForeground to set
	 */
	public void setSundayForeground(Color sundayForeground) {
		this.sundayForeground = sundayForeground;
		drawDayNames();
		drawDays();
	}

	/**
	 * Sets the weekday foreground.
	 * 
	 * @param weekdayForeground
	 *            The weekdayForeground to set
	 */
	public void setWeekdayForeground(Color weekdayForeground) {
		this.weekdayForeground = weekdayForeground;
		drawDayNames();
		drawDays();
	}

	/**
	 * Requests that the selected day also have the focus.
	 */
	public void setFocus() {
		if (selectedDay != null) {
			this.selectedDay.requestFocus();
		}
	}

	/**
	 * The decoration background is the background color of the day titles and
	 * the weeks of the year.
	 * 
	 * @return Returns true, if the decoration background is painted.
	 */
	public boolean isDecorationBackgroundVisible() {
		return decorationBackgroundVisible;
	}

	/**
	 * The decoration background is the background color of the day titles and
	 * the weeks of the year.
	 * 
	 * @param decorationBackgroundVisible
	 *            true, if the decoration background shall be painted.
	 */
	public void setDecorationBackgroundVisible(
			boolean decorationBackgroundVisible) {
		this.decorationBackgroundVisible = decorationBackgroundVisible;
		initDecorations();
	}

	/**
	 * The decoration border is the button border of the day titles and the
	 * weeks of the year.
	 * 
	 * @return Returns true, if the decoration border is painted.
	 */
	public boolean isDecorationBordersVisible() {
		return decorationBordersVisible;
	}

	public boolean isDayBordersVisible() {
		return dayBordersVisible;
	}

	/**
	 * The decoration border is the button border of the day titles and the
	 * weeks of the year.
	 * 
	 * @param decorationBordersVisible
	 *            true, if the decoration border shall be painted.
	 */
	public void setDecorationBordersVisible(boolean decorationBordersVisible) {
		this.decorationBordersVisible = decorationBordersVisible;
		initDecorations();
	}

	public void setDayBordersVisible(boolean dayBordersVisible) {
		this.dayBordersVisible = dayBordersVisible;
		if (initialized) {
			for (int x = 7; x < 49; x++) {
				if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
					days[x].setContentAreaFilled(dayBordersVisible);
				} else {
					days[x].setContentAreaFilled(true);
				}
				days[x].setBorderPainted(dayBordersVisible);
			}
		}
	}

	/**
	 * Updates the UI and sets the day button preferences.
	 */
	public void updateUI() {
		super.updateUI();
		setFont(Font.decode("Dialog Plain 11"));

		if (weekPanel != null) {
			weekPanel.updateUI();
		}
		if (initialized) {
			if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
				setDayBordersVisible(false);
				setDecorationBackgroundVisible(true);
				setDecorationBordersVisible(false);
			} else {
				setDayBordersVisible(true);
				setDecorationBackgroundVisible(decorationBackgroundVisible);
				setDecorationBordersVisible(decorationBordersVisible);
			}
		}
	}

	/**
	 * Sets a valid date range for selectable dates. If max is before min, the
	 * default range with no limitation is set.
	 * 
	 * @param min
	 *            the minimum selectable date or null (then the minimum date is
	 *            set to 01\01\0001)
	 * @param max
	 *            the maximum selectable date or null (then the maximum date is
	 *            set to 01\01\9999)
	 */
	public void setSelectableDateRange(Date min, Date max) {
		minMaxDateEvaluator.setMaxSelectableDate(max);
		minMaxDateEvaluator.setMinSelectableDate(min);
		drawDays();
	}

	/**
	 * Sets the maximum selectable date. If null, the date 01\01\9999 will be
	 * set instead.
	 * 
	 * @param max
	 *            the maximum selectable date
	 * 
	 * @return the maximum selectable date
	 */
	public Date setMaxSelectableDate(Date max) {
		Date maxSelectableDate = minMaxDateEvaluator.setMaxSelectableDate(max);
		drawDays();
		return maxSelectableDate;
	}

	/**
	 * Sets the minimum selectable date. If null, the date 01\01\0001 will be
	 * set instead.
	 * 
	 * @param min
	 *            the minimum selectable date
	 * 
	 * @return the minimum selectable date
	 */
	public Date setMinSelectableDate(Date min) {
		Date minSelectableDate = minMaxDateEvaluator.setMinSelectableDate(min);
		drawDays();
		return minSelectableDate;
	}

	/**
	 * Gets the maximum selectable date.
	 * 
	 * @return the maximum selectable date
	 */
	public Date getMaxSelectableDate() {
		return minMaxDateEvaluator.getMaxSelectableDate();
	}

	/**
	 * Gets the minimum selectable date.
	 * 
	 * @return the minimum selectable date
	 */
	public Date getMinSelectableDate() {
		return minMaxDateEvaluator.getMinSelectableDate();
	}

	/**
	 * Gets the maximum number of characters of a day name or 0. If 0 is
	 * returned, dateFormatSymbols.getShortWeekdays() will be used.
	 * 
	 * @return the maximum number of characters of a day name or 0.
	 */
	public int getMaxDayCharacters() {
		return maxDayCharacters;
	}

	/**
	 * Sets the maximum number of characters per day in the day bar. Valid
	 * values are 0-4. If set to 0, dateFormatSymbols.getShortWeekdays() will be
	 * used, otherwise theses strings will be reduced to the maximum number of
	 * characters.
	 * 
	 * @param maxDayCharacters
	 *            the maximum number of characters of a day name.
	 */
	public void setMaxDayCharacters(int maxDayCharacters) {
		if (maxDayCharacters == this.maxDayCharacters) {
			return;
		}

		if (maxDayCharacters < 0 || maxDayCharacters > 4) {
			this.maxDayCharacters = 0;
		} else {
			this.maxDayCharacters = maxDayCharacters;
		}
		drawDayNames();
		drawDays();
		invalidate();
	}

	class DecoratorButton extends JButton {
		private static final long serialVersionUID = -5306477668406547496L;

		public DecoratorButton() {
			setBackground(decorationBackgroundColor);
			setContentAreaFilled(decorationBackgroundVisible);
			setBorderPainted(decorationBordersVisible);
		}

		public void addMouseListener(MouseListener l) {
		}

		public boolean isFocusable() {
			return false;
		}

		public void paint(Graphics g) {
			if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
				// this is a hack to get the background painted
				// when using Windows Look & Feel
				if (decorationBackgroundVisible) {
					g.setColor(decorationBackgroundColor);
				} else {
					g.setColor(days[7].getBackground());
				}
				g.fillRect(0, 0, getWidth(), getHeight());
				if (isBorderPainted()) {
					setContentAreaFilled(true);
				} else {
					setContentAreaFilled(false);
				}
			}
			super.paint(g);
		}
	};

	public void addDateEvaluator(IDateEvaluator dateEvaluator) {
		dateEvaluators.add(dateEvaluator);
	}

	public void removeDateEvaluator(IDateEvaluator dateEvaluator) {
		dateEvaluators.remove(dateEvaluator);
	}
}

//import com.toedter.components.UTF8ResourceBundle;

/**
 * JCalendar is a bean for entering a date by choosing the year, month and day.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 159 $
 * @version $LastChangedDate: 2011-06-22 21:07:24 +0200 (Mi, 22 Jun 2011) $
 */
class JCalendar extends JPanel implements PropertyChangeListener {
	private Calendar calendar;
	private boolean initialized = false;
	private final JPanel monthYearPanel;
	private final JPanel specialButtonPanel;
	private boolean isTodayButtonVisible;
	private boolean isNullDateButtonVisible;
	private final String defaultTodayButtonText = "Today";
	private final String defaultNullDateButtonText = "No Date";
	private String todayButtonText;
	private String nullDateButtonText;

	/** the day chooser */
	protected JDayChooser dayChooser;

	/** indicates if weeks of year shall be visible */
	protected boolean weekOfYearVisible = true;

	/** the locale */
	protected Locale locale;

	/** the month chooser */
	protected JMonthChooser monthChooser;

	/** the year chooser */
	protected JYearChooser yearChooser;

	private final JButton todayButton;

	private final JButton nullDateButton;

	/**
	 * Default JCalendar constructor.
	 */
	public JCalendar() {
		this(null, null, true, true);
	}

	/**
	 * JCalendar constructor which allows the initial date to be set.
	 * 
	 * @param date
	 *            the date
	 */
	public JCalendar(Date date) {
		this(date, null, true, true);
	}

	/**
	 * JCalendar constructor which allows the initial calendar to be set.
	 * 
	 * @param calendar
	 *            the calendar
	 */
	public JCalendar(Calendar calendar) {
		this(null, null, true, true);
		setCalendar(calendar);
	}

	/**
	 * JCalendar constructor allowing the initial locale to be set.
	 * 
	 * @param locale
	 *            the new locale
	 */
	public JCalendar(Locale locale) {
		this(null, locale, true, true);
	}

	/**
	 * JCalendar constructor specifying both the initial date and locale.
	 * 
	 * @param date
	 *            the date
	 * @param locale
	 *            the new locale
	 */
	public JCalendar(Date date, Locale locale) {
		this(date, locale, true, true);
	}

	/**
	 * JCalendar constructor specifying both the initial date and the month
	 * spinner type.
	 * 
	 * @param date
	 *            the date
	 * @param monthSpinner
	 *            false, if no month spinner should be used
	 */
	public JCalendar(Date date, boolean monthSpinner) {
		this(date, null, monthSpinner, true);
	}

	/**
	 * JCalendar constructor specifying both the locale and the month spinner.
	 * 
	 * @param locale
	 *            the locale
	 * @param monthSpinner
	 *            false, if no month spinner should be used
	 */
	public JCalendar(Locale locale, boolean monthSpinner) {
		this(null, locale, monthSpinner, true);
	}

	/**
	 * JCalendar constructor specifying the month spinner type.
	 * 
	 * @param monthSpinner
	 *            false, if no month spinner should be used
	 */
	public JCalendar(boolean monthSpinner) {
		this(null, null, monthSpinner, true);
	}

	/**
	 * JCalendar constructor with month spinner parameter.
	 * 
	 * @param date
	 *            the date
	 * @param locale
	 *            the locale
	 * @param monthSpinner
	 *            false, if no month spinner should be used
	 * @param weekOfYearVisible
	 *            true, if weeks of year shall be visible
	 */
	public JCalendar(Date date, Locale locale, boolean monthSpinner,
			boolean weekOfYearVisible) {

		setName("JCalendar");

		// needed for setFont() etc.
		dayChooser = null;
		monthChooser = null;
		yearChooser = null;
		this.weekOfYearVisible = weekOfYearVisible;

		if (locale == null) {
			this.locale = Locale.getDefault();
		} else {
			this.locale = locale;
		}

		calendar = Calendar.getInstance(this.locale);

		setLayout(new BorderLayout());

		monthYearPanel = new JPanel();
		monthYearPanel.setLayout(new BorderLayout());

		monthChooser = new JMonthChooser(monthSpinner);
		yearChooser = new JYearChooser();
		monthChooser.setYearChooser(yearChooser);
		monthChooser.setLocale(this.locale);
		monthYearPanel.add(monthChooser, BorderLayout.WEST);
		monthYearPanel.add(yearChooser, BorderLayout.CENTER);
		monthYearPanel.setBorder(BorderFactory.createEmptyBorder());

		dayChooser = new JDayChooser(weekOfYearVisible);
		dayChooser.addPropertyChangeListener(this);
		dayChooser.setLocale(this.locale);

		monthChooser.setDayChooser(dayChooser);
		monthChooser.addPropertyChangeListener(this);
		yearChooser.setDayChooser(dayChooser);
		yearChooser.addPropertyChangeListener(this);
		add(monthYearPanel, BorderLayout.NORTH);
		add(dayChooser, BorderLayout.CENTER);

		specialButtonPanel = new JPanel();
		todayButton = new JButton();
		todayButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setDate(new Date());
			}
		});
		nullDateButton = new JButton();
		nullDateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dayChooser.firePropertyChange("day", 0, -1);
			}
		});
		specialButtonPanel.setVisible(false);
		add(specialButtonPanel, BorderLayout.SOUTH);

		// Set the initialized flag before setting the calendar. This will
		// cause the other components to be updated properly.
		if (date != null) {
			calendar.setTime(date);
		}

		initialized = true;

		setCalendar(calendar);
	}

	/**
	 * Returns the calendar property.
	 * 
	 * @return the value of the calendar property.
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * Gets the dayChooser attribute of the JCalendar object
	 * 
	 * @return the dayChooser value
	 */
	public JDayChooser getDayChooser() {
		return dayChooser;
	}

	/**
	 * Returns the locale.
	 * 
	 * @return the value of the locale property.
	 * 
	 * @see #setLocale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Gets the monthChooser attribute of the JCalendar object
	 * 
	 * @return the monthChooser value
	 */
	public JMonthChooser getMonthChooser() {
		return monthChooser;
	}

	/**
	 * Gets the yearChooser attribute of the JCalendar object
	 * 
	 * @return the yearChooser value
	 */
	public JYearChooser getYearChooser() {
		return yearChooser;
	}

	/**
	 * Indicates if the weeks of year are visible..
	 * 
	 * @return boolean true, if weeks of year are visible
	 */
	public boolean isWeekOfYearVisible() {
		return dayChooser.isWeekOfYearVisible();
	}

	/**
	 * JCalendar is a PropertyChangeListener, for its day, month and year
	 * chooser.
	 * 
	 * @param evt
	 *            the property change event
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (calendar != null) {
			Calendar c = (Calendar) calendar.clone();

			if (evt.getPropertyName().equals("day")) {
				c.set(Calendar.DAY_OF_MONTH,
						((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("month")) {
				c.set(Calendar.MONTH, ((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("year")) {
				c.set(Calendar.YEAR, ((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("date")) {
				c.setTime((Date) evt.getNewValue());
				setCalendar(c, true);
			}
		}
	}

	/**
	 * Sets the background color.
	 * 
	 * @param bg
	 *            the new background
	 */
	public void setBackground(Color bg) {
		super.setBackground(bg);

		if (dayChooser != null) {
			dayChooser.setBackground(bg);
		}
	}

	/**
	 * Sets the calendar property. This is a bound property.
	 * 
	 * @param c
	 *            the new calendar
	 * @throws NullPointerException
	 *             - if c is null;
	 * @see #getCalendar
	 */
	public void setCalendar(Calendar c) {
		setCalendar(c, true);
	}

	/**
	 * Sets the calendar attribute of the JCalendar object
	 * 
	 * @param c
	 *            the new calendar value
	 * @param update
	 *            the new calendar value
	 * @throws NullPointerException
	 *             - if c is null;
	 */
	private void setCalendar(Calendar c, boolean update) {
		if (c == null) {
			setDate(null);
		}
		Calendar oldCalendar = calendar;
		calendar = c;

		if (update) {
			// Thanks to Jeff Ulmer for correcting a bug in the sequence :)
			yearChooser.setYear(c.get(Calendar.YEAR));
			monthChooser.setMonth(c.get(Calendar.MONTH));
			dayChooser.setDay(c.get(Calendar.DATE));
		}

		firePropertyChange("calendar", oldCalendar, calendar);
	}

	/**
	 * Enable or disable the JCalendar.
	 * 
	 * @param enabled
	 *            the new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		if (dayChooser != null) {
			dayChooser.setEnabled(enabled);
			monthChooser.setEnabled(enabled);
			yearChooser.setEnabled(enabled);
		}
	}

	/**
	 * Returns true, if enabled.
	 * 
	 * @return true, if enabled.
	 */
	public boolean isEnabled() {
		return super.isEnabled();
	}

	/**
	 * Sets the font property.
	 * 
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		super.setFont(font);

		if (dayChooser != null) {
			dayChooser.setFont(font);
			monthChooser.setFont(font);
			yearChooser.setFont(font);
		}
	}

	/**
	 * Sets the foreground color.
	 * 
	 * @param fg
	 *            the new foreground
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);

		if (dayChooser != null) {
			dayChooser.setForeground(fg);
			monthChooser.setForeground(fg);
			yearChooser.setForeground(fg);
		}
	}

	/**
	 * Sets the locale property. This is a bound property.
	 * 
	 * @param l
	 *            the new locale value
	 * 
	 * @see #getLocale
	 */
	public void setLocale(Locale l) {
		if (!initialized) {
			super.setLocale(l);
		} else {
			Locale oldLocale = locale;
			locale = l;
			dayChooser.setLocale(locale);
			monthChooser.setLocale(locale);
			//relayoutSpecialButtonPanel();
			firePropertyChange("locale", oldLocale, locale);
		}
	}

	/**
	 * Sets the week of year visible.
	 * 
	 * @param weekOfYearVisible
	 *            true, if weeks of year shall be visible
	 */
	public void setWeekOfYearVisible(boolean weekOfYearVisible) {
		dayChooser.setWeekOfYearVisible(weekOfYearVisible);
		setLocale(locale); // hack for doing complete new layout :)
	}

	/**
	 * Gets the visibility of the decoration background.
	 * 
	 * @return true, if the decoration background is visible.
	 */
	public boolean isDecorationBackgroundVisible() {
		return dayChooser.isDecorationBackgroundVisible();
	}

	/**
	 * Sets the decoration background visible.
	 * 
	 * @param decorationBackgroundVisible
	 *            true, if the decoration background should be visible.
	 */
	public void setDecorationBackgroundVisible(
			boolean decorationBackgroundVisible) {
		dayChooser.setDecorationBackgroundVisible(decorationBackgroundVisible);
		setLocale(locale); // hack for doing complete new layout :)
	}

	/**
	 * Gets the visibility of the decoration border.
	 * 
	 * @return true, if the decoration border is visible.
	 */
	public boolean isDecorationBordersVisible() {
		return dayChooser.isDecorationBordersVisible();
	}

	/**
	 * Sets the decoration borders visible.
	 * 
	 * @param decorationBordersVisible
	 *            true, if the decoration borders should be visible.
	 */
	public void setDecorationBordersVisible(boolean decorationBordersVisible) {
		dayChooser.setDecorationBordersVisible(decorationBordersVisible);
		setLocale(locale); // hack for doing complete new layout :)
	}

	/**
	 * Returns the color of the decoration (day names and weeks).
	 * 
	 * @return the color of the decoration (day names and weeks).
	 */
	public Color getDecorationBackgroundColor() {
		return dayChooser.getDecorationBackgroundColor();
	}

	/**
	 * Sets the background of days and weeks of year buttons.
	 * 
	 * @param decorationBackgroundColor
	 *            the background color
	 */
	public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
		dayChooser.setDecorationBackgroundColor(decorationBackgroundColor);
	}

	/**
	 * Returns the Sunday foreground.
	 * 
	 * @return Color the Sunday foreground.
	 */
	public Color getSundayForeground() {
		return dayChooser.getSundayForeground();
	}

	/**
	 * Returns the weekday foreground.
	 * 
	 * @return Color the weekday foreground.
	 */
	public Color getWeekdayForeground() {
		return dayChooser.getWeekdayForeground();
	}

	/**
	 * Sets the Sunday foreground.
	 * 
	 * @param sundayForeground
	 *            the sundayForeground to set
	 */
	public void setSundayForeground(Color sundayForeground) {
		dayChooser.setSundayForeground(sundayForeground);
	}

	/**
	 * Sets the weekday foreground.
	 * 
	 * @param weekdayForeground
	 *            the weekdayForeground to set
	 */
	public void setWeekdayForeground(Color weekdayForeground) {
		dayChooser.setWeekdayForeground(weekdayForeground);
	}

	/**
	 * Returns a Date object.
	 * 
	 * @return a date object constructed from the calendar property.
	 */
	public Date getDate() {
		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * Sets the date. Fires the property change "date".
	 * 
	 * @param date
	 *            the new date.
	 * @throws NullPointerException
	 *             - if the date is null
	 */
	public void setDate(Date date) {
		Date oldDate = calendar.getTime();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		yearChooser.setYear(year);
		monthChooser.setMonth(month);
		dayChooser.setCalendar(calendar);
		dayChooser.setDay(day);

		firePropertyChange("date", oldDate, date);
	}

	/**
	 * Sets a valid date range for selectable dates. If max is before min, the
	 * default range with no limitation is set.
	 * 
	 * @param min
	 *            the minimum selectable date or null (then the minimum date is
	 *            set to 01\01\0001)
	 * @param max
	 *            the maximum selectable date or null (then the maximum date is
	 *            set to 01\01\9999)
	 */
	public void setSelectableDateRange(Date min, Date max) {
		dayChooser.setSelectableDateRange(min, max);
	};

	/**
	 * Gets the minimum selectable date.
	 * 
	 * @return the minimum selectable date
	 */
	public Date getMaxSelectableDate() {
		return dayChooser.getMaxSelectableDate();
	}

	/**
	 * Gets the maximum selectable date.
	 * 
	 * @return the maximum selectable date
	 */
	public Date getMinSelectableDate() {
		return dayChooser.getMinSelectableDate();
	}

	/**
	 * Sets the maximum selectable date.
	 * 
	 * @param max
	 *            maximum selectable date
	 */
	public void setMaxSelectableDate(Date max) {
		dayChooser.setMaxSelectableDate(max);
	}

	/**
	 * Sets the minimum selectable date.
	 * 
	 * @param min
	 *            minimum selectable date
	 */
	public void setMinSelectableDate(Date min) {
		dayChooser.setMinSelectableDate(min);
	}

	/**
	 * Gets the maximum number of characters of a day name or 0. If 0 is
	 * returned, dateFormatSymbols.getShortWeekdays() will be used.
	 * 
	 * @return the maximum number of characters of a day name or 0.
	 */
	public int getMaxDayCharacters() {
		return dayChooser.getMaxDayCharacters();
	}

	/**
	 * Sets the maximum number of characters per day in the day bar. Valid
	 * values are 0-4. If set to 0, dateFormatSymbols.getShortWeekdays() will be
	 * used, otherwise theses strings will be reduced to the maximum number of
	 * characters.
	 * 
	 * @param maxDayCharacters
	 *            the maximum number of characters of a day name.
	 */
	public void setMaxDayCharacters(int maxDayCharacters) {
		dayChooser.setMaxDayCharacters(maxDayCharacters);
	}

	/**
	 * Sets the Today button visible.
	 * 
	 * @param isTodayButtonVisible
	 *            true, is the today button shall be visible.
	 */
	public void setTodayButtonVisible(boolean isTodayButtonVisible) {
		this.isTodayButtonVisible = isTodayButtonVisible;
	}

	/**
	 * @return true, if Today button is visible.
	 */
	public boolean isTodayButtonVisible() {
		return isTodayButtonVisible;
	}

	/**
	 * Sets the Null Date button visible.
	 * 
	 * @param isNullDateButtonVisible
	 *            true, is the Null Date button shall be visible.
	 */
	public void setNullDateButtonVisible(boolean isNullDateButtonVisible) {
		this.isNullDateButtonVisible = isNullDateButtonVisible;
	}

	/**
	 * @return true, if Null Date button is visible.
	 */
	public boolean isNullDateButtonVisible() {
		return isNullDateButtonVisible;
	}

	/**
	 * @return the text of the Today button
	 */
	public String getTodayButtonText() {
		return todayButtonText;
	}

	/**
	 * Sets the Today button text.
	 * 
	 * @param todayButtonText
	 *            the new text
	 */
	public void setTodayButtonText(String todayButtonText) {
		if (todayButtonText != null & todayButtonText.trim().length() == 0) {
			this.todayButtonText = null;
		} else {
			this.todayButtonText = todayButtonText;
		}
	}

	/**
	 * @return the text of the Null Date button
	 */
	public String getNullDateButtonText() {
		return nullDateButtonText;
	}

	/**
	 * Sets the Null Date button text.
	 * 
	 * @param nullDateButtonText
	 *            the new text
	 */
	public void setNullDateButtonText(String nullDateButtonText) {
		if (nullDateButtonText != null
				& nullDateButtonText.trim().length() == 0) {
			this.nullDateButtonText = null;
		} else {
			this.nullDateButtonText = nullDateButtonText;
		}
	}
}

/**
 * All date editors that should be used by a JDateChooser have to implement this
 * interface.
 * @version $LastChangedRevision: 142 $
 * @version $LastChangedDate: 2011-06-05 07:06:03 +0200 (So, 05 Jun 2011) $
 */
interface IDateEditor {
	/**
	 * Returns the date.
	 */
	public Date getDate();

	/**
	 * Sets the date. This should be implemented as a bound property, firing the
	 * "date" property.
	 * 
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date);

	/**
	 * Sets the date format string, e.g. "MM/dd/yy". If the date format string
	 * is null or invalid, the date format string will be set to the MEDIUM
	 * Simple date format of the current locale.
	 * 
	 * @param dateFormatString
	 *            the date format string
	 */
	public void setDateFormatString(String dateFormatString);

	/**
	 * Returns the date format string.
	 * 
	 * @return the date format string
	 */
	public String getDateFormatString();

	/**
	 * Sets a valid date range for selectable dates. If max is before
	 * min, the default range with no limitation is set.
	 * 
	 * @param min
	 *            the minimum selectable date or null (then the minimum date should be
	 *            set to 01\01\0001)
	 * @param max
	 *            the maximum selectable date or null (then the maximum date should be
	 *            set to 01\01\9999)
	 */
	public void setSelectableDateRange(Date min, Date max) ;

	/**
	 * Gets the minimum selectable date.
	 * 
	 * @return the minimum selectable date
	 */
	public Date getMaxSelectableDate();
	
	/**
	 * Gets the maximum selectable date.
	 * 
	 * @return the maximum selectable date
	 */
	public Date getMinSelectableDate();

	/**
	 * Sets the maximum selectable date.
	 * 
	 * @param max maximum selectable date
	 */
	public void setMaxSelectableDate(Date max);

	/**
	 * Sets the minimum selectable date.
	 * 
	 * @param min minimum selectable date
	 */
	public void setMinSelectableDate(Date min);

	/**
	 * Returns the UI component, e.g. the actual JTextField implementing the
	 * editor.
	 * 
	 * @return the UI component
	 */
	public JComponent getUiComponent();

	/**
	 * Sets the locale. Usually this should have impact on the current date
	 * format string.
	 * 
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale);

	/**
	 * Enables or disables the UI compoment.
	 * 
	 * @param enabled
	 *            true, if the UI component should be enabled.
	 */
	public void setEnabled(boolean enabled);

	/**
	 * Adds a property change listener that should be added to the implementing
	 * UI component. The UI component should fire a "date" property if the date
	 * changes.
	 * 
	 * @param listener
	 *            the property change listener.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Adds a property change listener that should be added to the implementing
	 * UI component. The UI component should fire a "date" property if the date
	 * changes.
	 * 
	 * @param propertyName
	 *            the property name, e.g. "date"
	 * @param listener
	 *            the property change listener.
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Removes a property change listener.
	 * 
	 * @param listener
	 *            the property change listener.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Removes the listener from the date editor's property change listeners for the specific property.
	 * 
	 * @param propertyName
	 *            the property to listen for, e.g. "date"
	 * @param listener
	 *            the listener
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}


/**
 * A date chooser containing a date editor and a button, that makes a JCalendar
 * visible for choosing a date. If no date editor is specified, a
 * JTextFieldDateEditor is used as default.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 149 $
 * @version $LastChangedDate: 2011-06-07 19:05:02 +0200 (Di, 07 Jun 2011) $
 */
///***********************************************************************************************************************************************************
class JDateChooser extends JPanel implements ActionListener,PropertyChangeListener
{
	protected IDateEditor dateEditor;

	protected JButton calendarButton;

	protected JCalendar jcalendar;

	protected JPopupMenu popup;

	protected boolean isInitialized;

	protected boolean dateSelected;

	protected Date lastSelectedDate;

	private ChangeListener changeListener;

	/**
	 * Creates a new JDateChooser. By default, no date is set and the textfield
	 * is empty.
	 */
	public JDateChooser() {
		this(null, null, null, null);
	}

	/**
	 * Creates a new JDateChooser with given IDateEditor.
	 * 
	 * @param dateEditor
	 *            the dateEditor to be used used to display the date. if null, a
	 *            JTextFieldDateEditor is used.
	 */
	public JDateChooser(IDateEditor dateEditor) {
		this(null, null, null, dateEditor);
	}

	/**
	 * Creates a new JDateChooser.
	 * 
	 * @param date
	 *            the date or null
	 */
	public JDateChooser(Date date) {
		this(date, null);
	}

	/**
	 * Creates a new JDateChooser.
	 * 
	 * @param date
	 *            the date or null
	 * @param dateFormatString
	 *            the date format string or null (then MEDIUM SimpleDateFormat
	 *            format is used)
	 */
	public JDateChooser(Date date, String dateFormatString) {
		this(date, dateFormatString, null);
	}

	/**
	 * Creates a new JDateChooser.
	 * 
	 * @param date
	 *            the date or null
	 * @param dateFormatString
	 *            the date format string or null (then MEDIUM SimpleDateFormat
	 *            format is used)
	 * @param dateEditor
	 *            the dateEditor to be used used to display the date. if null, a
	 *            JTextFieldDateEditor is used.
	 */
	public JDateChooser(Date date, String dateFormatString,
			IDateEditor dateEditor) {
		this(null, date, dateFormatString, dateEditor);
	}

	/**
	 * Creates a new JDateChooser. If the JDateChooser is created with this
	 * constructor, the mask will be always visible in the date editor. Please
	 * note that the date pattern and the mask will not be changed if the locale
	 * of the JDateChooser is changed.
	 * 
	 * @param datePattern
	 *            the date pattern, e.g. "MM/dd/yy"
	 * @param maskPattern
	 *            the mask pattern, e.g. "##/##/##"
	 * @param placeholder
	 *            the place holder character, e.g. '_'
	 */
	public JDateChooser(String datePattern, String maskPattern, char placeholder) {
		this(null, null, datePattern, new JTextFieldDateEditor(datePattern,
				maskPattern, placeholder));
	}

	/**
	 * Creates a new JDateChooser.
	 * 
	 * @param jcal
	 *            the JCalendar to be used
	 * @param date
	 *            the date or null
	 * @param dateFormatString
	 *            the date format string or null (then MEDIUM Date format is
	 *            used)
	 * @param dateEditor
	 *            the dateEditor to be used used to display the date. if null, a
	 *            JTextFieldDateEditor is used.
	 */
	public JDateChooser(JCalendar jcal, Date date, String dateFormatString,	IDateEditor dateEditor) {
		setName("JDateChooser");
		this.dateEditor = dateEditor;
		if (this.dateEditor == null) {
			this.dateEditor = new JTextFieldDateEditor();
		}
		this.dateEditor.addPropertyChangeListener("date", this);

		if (jcal == null) {
			jcalendar = new JCalendar(date);
		} else {
			jcalendar = jcal;
			if (date != null) {
				jcalendar.setDate(date);
			}
		}
		setLayout(new BorderLayout());

		jcalendar.getDayChooser().addPropertyChangeListener("day", this);
		// always fire"day" property even if the user selects
		// the already selected day again
		jcalendar.getDayChooser().setAlwaysFireDayProperty(true);

		setDateFormatString(dateFormatString);
		setDate(date);

		// Display a calendar button with an icon
		ImageIcon icon = new ImageIcon("JDateChooserIcon.gif");

		calendarButton = new JButton(icon) {
			public boolean isFocusable() {
				return false;
			}
		};
		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		calendarButton.addActionListener(this);

		// Alt + 'C' selects the calendar.
		calendarButton.setMnemonic(KeyEvent.VK_C);

		add(calendarButton, BorderLayout.EAST);
		add(this.dateEditor.getUiComponent(), BorderLayout.CENTER);

		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		// calendarButton.addFocusListener(this);

		popup = new JPopupMenu()
			 {
				public void setVisible(boolean b) {
				Boolean isCanceled = (Boolean) getClientProperty("JPopupMenu.firePopupMenuCanceled");
				if (b
						|| (!b && dateSelected)
						|| ((isCanceled != null) && !b && isCanceled
								.booleanValue())) {
					super.setVisible(b);
				}
			}
		};

		popup.setLightWeightPopupEnabled(true);

		popup.add(jcalendar);

		lastSelectedDate = date;

		// Corrects a problem that occurred when the JMonthChooser's combobox is
		// displayed, and a click outside the popup does not close it.

		// The following idea was originally provided by forum user
		// podiatanapraia:
		changeListener = new ChangeListener() {
			boolean hasListened = false;

			public void stateChanged(ChangeEvent e) {
				if (hasListened) {
					hasListened = false;
					return;
				}
				if (popup.isVisible()
						&& JDateChooser.this.jcalendar.monthChooser
								.getComboBox().hasFocus()) {
					MenuElement[] me = MenuSelectionManager.defaultManager()
							.getSelectedPath();
					MenuElement[] newMe = new MenuElement[me.length + 1];
					newMe[0] = popup;
					for (int i = 0; i < me.length; i++) {
						newMe[i + 1] = me[i];
					}
					hasListened = true;
					MenuSelectionManager.defaultManager()
							.setSelectedPath(newMe);
				}
			}
		};
		MenuSelectionManager.defaultManager().addChangeListener(changeListener);
		// end of code provided by forum user podiatanapraia

		isInitialized = true;
	}

	/**
	 * Called when the calendar button was pressed.
	 * 
	 * @param e
	 *            the action event
	 */
	public void actionPerformed(ActionEvent e) {
		int x = calendarButton.getWidth()
				- (int) popup.getPreferredSize().getWidth();
		int y = calendarButton.getY() + calendarButton.getHeight();

		Calendar calendar = Calendar.getInstance();
		Date date = dateEditor.getDate();
		if (date != null) {
			calendar.setTime(date);
		}
		jcalendar.setCalendar(calendar);
		popup.show(calendarButton, x, y);
		dateSelected = false;
	}

	/**
	 * Listens for a "date" property change or a "day" property change event
	 * from the JCalendar. Updates the date editor and closes the popup.
	 * 
	 * @param evt
	 *            the event
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("day")) {
			if (popup.isVisible()) {
				dateSelected = true;
				popup.setVisible(false);
				if (((Integer)evt.getNewValue()).intValue() > 0) {
					setDate(jcalendar.getCalendar().getTime());
				} else {
					setDate(null);
				}
			}
		} else if (evt.getPropertyName().equals("date")) {
			if (evt.getSource() == dateEditor) {
				firePropertyChange("date", evt.getOldValue(), evt.getNewValue());
			} else {
				setDate((Date) evt.getNewValue());
			}
		}
	}

	/**
	 * Updates the UI of itself and the popup.
	 */
	public void updateUI() {
		super.updateUI();
		setEnabled(isEnabled());

		if (jcalendar != null) {
			SwingUtilities.updateComponentTreeUI(popup);
		}
	}

	/**
	 * Sets the locale.
	 * 
	 * @param l
	 *            The new locale value
	 */
	public void setLocale(Locale l) {
		super.setLocale(l);
		dateEditor.setLocale(l);
		jcalendar.setLocale(l);
	}

	/**
	 * Gets the date format string.
	 * 
	 * @return Returns the dateFormatString.
	 */
	public String getDateFormatString() {
		return dateEditor.getDateFormatString();
	}

	/**
	 * Sets the date format string. E.g "MMMMM d, yyyy" will result in "July 21,
	 * 2004" if this is the selected date and locale is English.
	 * 
	 * @param dfString
	 *            The dateFormatString to set.
	 */
	public void setDateFormatString(String dfString) {
		dateEditor.setDateFormatString(dfString);
		invalidate();
	}

	/**
	 * Returns the date. If the JDateChooser is started with a null date and no
	 * date was set by the user, null is returned.
	 * 
	 * @return the current date
	 */
	public Date getDate() {
		return dateEditor.getDate();
	}

	/**
	 * Sets the date. Fires the property change "date" if date != null.
	 * 
	 * @param date
	 *            the new date.
	 */
	public void setDate(Date date) {
		dateEditor.setDate(date);
		if (getParent() != null) {
			getParent().invalidate();
		}
	}

	/**
	 * Returns the calendar. If the JDateChooser is started with a null date (or
	 * null calendar) and no date was set by the user, null is returned.
	 * 
	 * @return the current calendar
	 */
	public Calendar getCalendar() {
		Date date = getDate();
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Sets the calendar. Value null will set the null date on the date editor.
	 * 
	 * @param calendar
	 *            the calendar.
	 */
	public void setCalendar(Calendar calendar) {
		if (calendar == null) {
			dateEditor.setDate(null);
		} else {
			dateEditor.setDate(calendar.getTime());
		}
	}

	/**
	 * Enable or disable the JDateChooser.
	 * 
	 * @param enabled
	 *            the new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (dateEditor != null) {
			dateEditor.setEnabled(enabled);
			calendarButton.setEnabled(enabled);
		}
	}

	/**
	 * Returns true, if enabled.
	 * 
	 * @return true, if enabled.
	 */
	public boolean isEnabled() {
		return super.isEnabled();
	}

	/**
	 * Sets the icon of the buuton.
	 * 
	 * @param icon
	 *            The new icon
	 */
	public void setIcon(ImageIcon icon) {
		calendarButton.setIcon(icon);
	}

	/**
	 * Sets the font of all subcomponents.
	 * 
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		if (isInitialized) {
			dateEditor.getUiComponent().setFont(font);
			jcalendar.setFont(font);
		}
		super.setFont(font);
	}

	/**
	 * Returns the JCalendar component. THis is usefull if you want to set some
	 * properties.
	 * 
	 * @return the JCalendar
	 */
	public JCalendar getJCalendar() {
		return jcalendar;
	}

	/**
	 * Returns the calendar button.
	 * 
	 * @return the calendar button
	 */
	public JButton getCalendarButton() {
		return calendarButton;
	}

	/**
	 * Returns the date editor.
	 * 
	 * @return the date editor
	 */
	public IDateEditor getDateEditor() {
		return dateEditor;
	}

	/**
	 * Sets a valid date range for selectable dates. If max is before min, the
	 * default range with no limitation is set.
	 * 
	 * @param min
	 *            the minimum selectable date or null (then the minimum date is
	 *            set to 01\01\0001)
	 * @param max
	 *            the maximum selectable date or null (then the maximum date is
	 *            set to 01\01\9999)
	 */
	public void setSelectableDateRange(Date min, Date max) {
		jcalendar.setSelectableDateRange(min, max);
		dateEditor.setSelectableDateRange(jcalendar.getMinSelectableDate(),
				jcalendar.getMaxSelectableDate());
	}

	public void setMaxSelectableDate(Date max) {
		jcalendar.setMaxSelectableDate(max);
		dateEditor.setMaxSelectableDate(max);
	}

	public void setMinSelectableDate(Date min) {
		jcalendar.setMinSelectableDate(min);
		dateEditor.setMinSelectableDate(min);
	}

	/**
	 * Gets the maximum selectable date.
	 * 
	 * @return the maximum selectable date
	 */
	public Date getMaxSelectableDate() {
		return jcalendar.getMaxSelectableDate();
	}

	/**
	 * Gets the minimum selectable date.
	 * 
	 * @return the minimum selectable date
	 */
	public Date getMinSelectableDate() {
		return jcalendar.getMinSelectableDate();
	}

	/**
	 * Should only be invoked if the JDateChooser is not used anymore. Due to
	 * popup handling it had to register a change listener to the default menu
	 * selection manager which will be unregistered here. Use this method to
	 * cleanup possible memory leaks.
	 */
	public void cleanup() {
		MenuSelectionManager.defaultManager().removeChangeListener(
				changeListener);
		changeListener = null;
	}

	/*

	/**
		 
		JDateChooser dateChooser = new JDateChooser();
		// JDateChooser dateChooser = new JDateChooser(null, new Date(), null, null);
		// dateChooser.setLocale(new Locale("de"));
		// dateChooser.setDateFormatString("dd. MMMM yyyy");

		// dateChooser.setPreferredSize(new Dimension(130, 20));
		// dateChooser.setFont(new Font("Verdana", Font.PLAIN, 10));
		// dateChooser.setDateFormatString("yyyy-MM-dd HH:mm");

		// ImageIcon icon = new ImageIcon("JMonthChooserColor32.gif");
		// dateChooser.setIcon(icon);
	*/

}


/************************************************************************************************************************************************************
 * JMonthChooser is a bean for choosing a month.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 159 $
 * @version $LastChangedDate: 2011-06-22 21:07:24 +0200 (Mi, 22 Jun 2011) $
 */
class JMonthChooser extends JPanel implements ItemListener,	ChangeListener {
	/** true, if the month chooser has a spinner component */
	protected boolean hasSpinner;

	private Locale locale;

	private int month;

	private int oldSpinnerValue = 0;

	// needed for comparison
	private JDayChooser dayChooser;

	private JYearChooser yearChooser;

	private JComboBox comboBox;

	private JSpinner spinner;

	private boolean initialized;

	private boolean localInitialize;

	/**
	 * Default JMonthChooser constructor.
	 */
	public JMonthChooser() {
		this(true);
	}

	/**
	 * JMonthChooser constructor with month spinner parameter.
	 * 
	 * @param hasSpinner
	 *            true, if the month chooser should have a spinner component
	 */
	public JMonthChooser(boolean hasSpinner) {
		super();
		setName("JMonthChooser");
		this.hasSpinner = hasSpinner;

		setLayout(new BorderLayout());

		comboBox = new JComboBox();
		comboBox.addItemListener(this);

		// comboBox.addPopupMenuListener(this);
		locale = Locale.getDefault();
		initNames();

		if (hasSpinner) {
			spinner = new JSpinner() {
				private static final long serialVersionUID = 1L;

				private JTextField textField = new JTextField();

				public Dimension getPreferredSize() {
					Dimension size = super.getPreferredSize();
					return new Dimension(size.width, textField
							.getPreferredSize().height);
				}
			};
			spinner.addChangeListener(this);
			spinner.setEditor(comboBox);
			comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
			updateUI();

			add(spinner, BorderLayout.WEST);
		} else {
			add(comboBox, BorderLayout.WEST);
		}

		initialized = true;
		setMonth(Calendar.getInstance().get(Calendar.MONTH));
	}

	/**
	 * Initializes the locale specific month names.
	 */
	public void initNames() {
		localInitialize = true;

		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String[] monthNames = dateFormatSymbols.getMonths();

		if (comboBox.getItemCount() == 12) {
			comboBox.removeAllItems();
		}

		for (int i = 0; i < 12; i++) {
			comboBox.addItem(monthNames[i]);
		}

		localInitialize = false;
		comboBox.setSelectedIndex(month);
	}

	/**
	 * Is invoked if the state of the spinner changes.
	 * 
	 * @param e
	 *            the change event.
	 */
	public void stateChanged(ChangeEvent e) {
		SpinnerNumberModel model = (SpinnerNumberModel) ((JSpinner) e
				.getSource()).getModel();
		int value = model.getNumber().intValue();
		boolean increase = (value > oldSpinnerValue) ? true : false;
		oldSpinnerValue = value;

		int month = getMonth();

		if (increase) {
			month += 1;

			if (month == 12) {
				month = 0;

				if (yearChooser != null) {
					int year = yearChooser.getYear();
					year += 1;
					yearChooser.setYear(year);
				}
			}
		} else {
			month -= 1;

			if (month == -1) {
				month = 11;

				if (yearChooser != null) {
					int year = yearChooser.getYear();
					year -= 1;
					yearChooser.setYear(year);
				}
			}
		}

		setMonth(month);
	}

	/**
	 * The ItemListener for the months.
	 * 
	 * @param e
	 *            the item event
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int index = comboBox.getSelectedIndex();

			if ((index >= 0) && (index != month)) {
				setMonth(index, false);
			}
		}
	}

	/**
	 * Sets the month attribute of the JMonthChooser object. Fires a property
	 * change "month".
	 * 
	 * @param newMonth
	 *            the new month value
	 * @param select
	 *            true, if the month should be selcted in the combo box.
	 */
	private void setMonth(int newMonth, boolean select) {
		if (!initialized || localInitialize) {
			return;
		}

		int oldMonth = month;
		month = newMonth;

		if (select) {
			comboBox.setSelectedIndex(month);
		}

		if (dayChooser != null) {
			dayChooser.setMonth(month);
		}

		firePropertyChange("month", oldMonth, month);
	}

	/**
	 * Sets the month. This is a bound property. Valuse are valid between 0
	 * (January) and 11 (December). A value < 0 will be treated as 0, a value >
	 * 11 will be treated as 11.
	 * 
	 * @param newMonth
	 *            the new month value
	 * 
	 * @see #getMonth
	 */
	public void setMonth(int newMonth) {
		if (newMonth < 0 || newMonth == Integer.MIN_VALUE) {
			setMonth(0, true);
		} else if (newMonth > 11) {
			setMonth(11, true);
		} else {
			setMonth(newMonth, true);
		}
	}

	/**
	 * Returns the month.
	 * 
	 * @return the month value
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Convenience method set a day chooser.
	 * 
	 * @param dayChooser
	 *            the day chooser
	 */
	public void setDayChooser(JDayChooser dayChooser) {
		this.dayChooser = dayChooser;
	}

	/**
	 * Convenience method set a year chooser. If set, the spin for the month
	 * buttons will spin the year as well
	 * 
	 * @param yearChooser
	 *            the new yearChooser value
	 */
	public void setYearChooser(JYearChooser yearChooser) {
		this.yearChooser = yearChooser;
	}

	/**
	 * Returns the locale.
	 * 
	 * @return the locale value
	 * 
	 * @see #setLocale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Set the locale and initializes the new month names.
	 * 
	 * @param l
	 *            the new locale value
	 * 
	 * @see #getLocale
	 */
	public void setLocale(Locale l) {
		if (!initialized) {
			super.setLocale(l);
		} else {
			locale = l;
			initNames();
		}
	}

	/**
	 * Enable or disable the JMonthChooser.
	 * 
	 * @param enabled
	 *            the new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		comboBox.setEnabled(enabled);

		if (spinner != null) {
			spinner.setEnabled(enabled);
		}
	}

	/**
	 * Returns the month chooser's comboBox text area (which allow the focus to
	 * be set to it).
	 * 
	 * @return the combo box
	 */
	public Component getComboBox() {
		return this.comboBox;
	}

	/**
	 * Returns the month chooser's comboBox bar (which allow the focus to be set
	 * to it).
	 * 
	 * @return Component the spinner or null, if the month chooser has no
	 *         spinner
	 */
	public Component getSpinner() {
		// Returns <null> if there is no spinner.
		return spinner;
	}

	/**
	 * Returns the type of spinner the month chooser is using.
	 * 
	 * @return true, if the month chooser has a spinner
	 */
	public boolean hasSpinner() {
		return hasSpinner;
	}

    /**
     * Sets the font for this component.
     *
     * @param font the desired <code>Font</code> for this component
     */
	public void setFont(Font font) {
		if (comboBox != null) {
			comboBox.setFont(font);
		}
		super.setFont(font);
	}

	/**
	 * Updates the UI.
	 * 
	 * @see javax.swing.JPanel#updateUI()
	 */
	public void updateUI() {
		final JSpinner testSpinner = new JSpinner();
		if (spinner != null) {
			if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
				spinner.setBorder(testSpinner.getBorder());
			} else {
				spinner.setBorder(new EmptyBorder(0, 0, 0, 0));
			}
		}
	}
}


/********************************************************************************************************************************************************************************
 * JTextFieldDateEditor is the default editor used by JDateChooser. It is a
 * formatted text field, that colores valid dates green/black and invalid dates
 * red. The date format patten and mask can be set manually. If not set, the
 * MEDIUM pattern of a SimpleDateFormat with regards to the actual locale is
 * used.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 97 $
 * @version $LastChangedDate: 2006-05-24 17:30:41 +0200 (Mi, 24 Mai 2006) $
 **********************************************************************************************************************/
 
class JTextFieldDateEditor extends JFormattedTextField implements 
IDateEditor, CaretListener, FocusListener, ActionListener {

	protected Date date;

	protected SimpleDateFormat dateFormatter;

	protected MaskFormatter maskFormatter;

	protected String datePattern;

	protected String maskPattern;

	protected char placeholder;

	protected Color darkGreen;

	protected DateUtil dateUtil;

	private boolean isMaskVisible;

	private boolean ignoreDatePatternChange;

	private int hours;

	private int minutes;

	private int seconds;

	private int millis;

	private Calendar calendar;

	public JTextFieldDateEditor() {
		this(false, null, null, ' ');
	}

	public JTextFieldDateEditor(String datePattern, String maskPattern, char placeholder) {
		this(true, datePattern, maskPattern, placeholder);
	}

	public JTextFieldDateEditor(boolean showMask, String datePattern, String maskPattern,
			char placeholder) {
		dateFormatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
		dateFormatter.setLenient(false);

		setDateFormatString(datePattern);
		if (datePattern != null) {
			ignoreDatePatternChange = true;
		}

		this.placeholder = placeholder;

		if (maskPattern == null) {
			this.maskPattern = createMaskFromDatePattern(this.datePattern);
		} else {
			this.maskPattern = maskPattern;
		}

		setToolTipText(this.datePattern);
		setMaskVisible(showMask);

		addCaretListener(this);
		addFocusListener(this);
		addActionListener(this);
		darkGreen = new Color(0, 150, 0);

		calendar = Calendar.getInstance();

		dateUtil = new DateUtil();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getDate()
	 */
	public Date getDate() {
		try {
			calendar.setTime(dateFormatter.parse(getText()));
			calendar.set(Calendar.HOUR_OF_DAY, hours);
			calendar.set(Calendar.MINUTE, minutes);
			calendar.set(Calendar.SECOND, seconds);
			calendar.set(Calendar.MILLISECOND, millis);
			date = calendar.getTime();
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setDate(java.util.Date)
	 */
	public void setDate(Date date) {
		setDate(date, true);
	}

	/**
	 * Sets the date.
	 * 
	 * @param date the date
	 * @param firePropertyChange
	 *            true, if the date property should be fired.
	 */
	protected void setDate(Date date, boolean firePropertyChange) {
		Date oldDate = this.date;
		this.date = date;

		if (date == null) {
			setText("");
		} else {
			calendar.setTime(date);
			hours = calendar.get(Calendar.HOUR_OF_DAY);
			minutes = calendar.get(Calendar.MINUTE);
			seconds = calendar.get(Calendar.SECOND);
			millis = calendar.get(Calendar.MILLISECOND);

			String formattedDate = dateFormatter.format(date);
			try {
				setText(formattedDate);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		if (date != null && dateUtil.checkDate(date)) {
			setForeground(Color.BLACK);

		}

		if (firePropertyChange) {
			firePropertyChange("date", oldDate, date);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setDateFormatString(java.lang.String)
	 */
	public void setDateFormatString(String dateFormatString) {
		if (ignoreDatePatternChange) {
			return;
		}

		try {
			dateFormatter.applyPattern(dateFormatString);
		} catch (RuntimeException e) {
			dateFormatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
			dateFormatter.setLenient(false);
		}
		this.datePattern = dateFormatter.toPattern();
		setToolTipText(this.datePattern);
		setDate(date, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getDateFormatString()
	 */
	public String getDateFormatString() {
		return datePattern;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getUiComponent()
	 */
	public JComponent getUiComponent() {
		return this;
	}

	/**
	 * After any user input, the value of the textfield is proofed. Depending on
	 * being a valid date, the value is colored green or red.
	 * 
	 * @param event
	 *            the caret event
	 */
	public void caretUpdate(CaretEvent event) {
		String text = getText().trim();
		String emptyMask = maskPattern.replace('#', placeholder);

		if (text.length() == 0 || text.equals(emptyMask)) {
			setForeground(Color.BLACK);
			return;
		}

		try {
			Date date = dateFormatter.parse(getText());
			if (dateUtil.checkDate(date)) {
				setForeground(darkGreen);
			} else {
				setForeground(Color.RED);
			}
		} catch (Exception e) {
			setForeground(Color.RED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent focusEvent) {
		checkText();
	}

	private void checkText() {
		try {
			Date date = dateFormatter.parse(getText());
			setDate(date, true);
		} catch (Exception e) {
			// ignore
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale locale) {
		if (locale == getLocale() || ignoreDatePatternChange) {
			return;
		}

		super.setLocale(locale);
		dateFormatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		setToolTipText(dateFormatter.toPattern());

		setDate(date, false);
		doLayout();
	}

	/**
	 * Creates a mask from a date pattern. This is a very simple (and
	 * incomplete) implementation thet works only with numbers. A date pattern
	 * of "MM/dd/yy" will result in the mask "##/##/##". Probably you want to
	 * override this method if it does not fit your needs.
	 * 
	 * @param datePattern
	 *            the date pattern
	 * @return the mask
	 */
	public String createMaskFromDatePattern(String datePattern) {
		String symbols = "GyMdkHmsSEDFwWahKzZ";
		String mask = "";
		for (int i = 0; i < datePattern.length(); i++) {
			char ch = datePattern.charAt(i);
			boolean symbolFound = false;
			for (int n = 0; n < symbols.length(); n++) {
				if (symbols.charAt(n) == ch) {
					mask += "#";
					symbolFound = true;
					break;
				}
			}
			if (!symbolFound) {
				mask += ch;
			}
		}
		return mask;
	}

	/**
	 * Returns true, if the mask is visible.
	 * 
	 * @return true, if the mask is visible
	 */
	public boolean isMaskVisible() {
		return isMaskVisible;
	}

	/**
	 * Sets the mask visible.
	 * 
	 * @param isMaskVisible
	 *            true, if the mask should be visible
	 */
	public void setMaskVisible(boolean isMaskVisible) {
		this.isMaskVisible = isMaskVisible;
		if (isMaskVisible) {
			if (maskFormatter == null) {
				try {
					maskFormatter = new MaskFormatter(createMaskFromDatePattern(datePattern));
					maskFormatter.setPlaceholderCharacter(this.placeholder);
					maskFormatter.install(this);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Returns the preferred size. If a date pattern is set, it is the size the
	 * date pattern would take.
	 */
	public Dimension getPreferredSize() {
		if (datePattern != null) {
			return new JTextField(datePattern).getPreferredSize();
		}
		return super.getPreferredSize();
	}

	/**
	 * Validates the typed date and sets it (only if it is valid).
	 */
	public void actionPerformed(ActionEvent e) {
		checkText();
	}

	/**
	 * Enables and disabled the compoment. It also fixes the background bug
	 * 4991597 and sets the background explicitely to a
	 * TextField.inactiveBackground.
	 */
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		if (!b) {
			super.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getMaxSelectableDate()
	 */
	public Date getMaxSelectableDate() {
		return dateUtil.getMaxSelectableDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getMinSelectableDate()
	 */
	public Date getMinSelectableDate() {
		return dateUtil.getMinSelectableDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setMaxSelectableDate(java.util.Date)
	 */
	public void setMaxSelectableDate(Date max) {
		dateUtil.setMaxSelectableDate(max);
		checkText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setMinSelectableDate(java.util.Date)
	 */
	public void setMinSelectableDate(Date min) {
		dateUtil.setMinSelectableDate(min);
		checkText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setSelectableDateRange(java.util.Date,
	 *      java.util.Date)
	 */
	public void setSelectableDateRange(Date min, Date max) {
		dateUtil.setSelectableDateRange(min, max);
		checkText();
	}

}
 
///************************************************************************************************************************

