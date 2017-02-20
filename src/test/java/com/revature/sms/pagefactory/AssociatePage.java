package com.revature.sms.pagefactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.revature.sms.util.Utils;

public class AssociatePage extends HomePage {

	@FindBy(xpath = "//*[@name=\"contentView\"]/md-card/md-toolbar/div/button[1]/md-icon")
	public WebElement checkincheckout;

	@FindBy(xpath = "//*[@name=\"contentView\"]/md-card/md-toolbar/div/button[2]/md-icon")
	public WebElement certification;
	
	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/button[1]")
	public WebElement prevWeek;

	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/button[2]")
	public WebElement nextWeek;

	@FindBy(xpath = "//*[@name=\"mainView\"]/div/md-card/div/div[2]/div")
	public WebElement weekOf;

	public AssociatePage(WebDriver driver) {
		super(driver);
	}
	
	
}