package com.avito.objects;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;

public class appPageObjects {

	@AndroidFindBy(id = "com.avito.android:id/layout_button")
	public MobileElement addPostButtonInHomeScreen;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.avito.android:id/toolbar']//android.widget.ImageButton")
	public MobileElement menuButton;
	
	@AndroidFindBy(xpath = "//*[@text='РАЗМЕСТИТЬ ОБЪЯВЛЕНИЕ']")
	public MobileElement createAdButton;
	
	@AndroidFindBy(id = "com.avito.android:id/account_image")
	public MobileElement userProfileButton;
	
	@AndroidFindBy(id = "com.avito.android:id/login")
	public MobileElement loginButton;
	
	@AndroidFindBy(id = "com.avito.android:id/registration")
	public MobileElement userRegistration;
	
	@AndroidFindBy(className = "android.widget.EditText")
	public MobileElement mobileInRegistration;
	
	@AndroidFindBy(id = "com.avito.android:id/continue_button")
	public MobileElement continueButtonInRegistration;
	
	@AndroidFindBy(className = "android.widget.EditText")
	public MobileElement accessCodeFieldInRegistration;
	
	@AndroidFindBy(id = "com.avito.android:id/send_button")
	public MobileElement sendButtonInRegistration;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.avito.android:id/toolbar']//	android.widget.ImageButton")
	public MobileElement backButtonInRegistration;
	
	@AndroidFindBy(xpath = "//*[@text='Создать новую учётную запись']")
	public MobileElement createAnotherAccount;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.avito.android:id/name_view']//android.widget.EditText")
	public MobileElement nameFieldInRegistraton;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.avito.android:id/password_view']//android.widget.EditText")
	public MobileElement passwordFieldInRegistration;
	
	@AndroidFindBy(id = "com.avito.android:id/register_button")
	public MobileElement registerButton;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.avito.android:id/login_view']//android.widget.EditText")
	public MobileElement mobileNumber;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.avito.android:id/password_view']//android.widget.EditText")
	public MobileElement password;
	
	@AndroidFindBy(id = "com.avito.android:id/login_button")
	public MobileElement loginToSubmitButton;
	
	@AndroidFindBy(id = "android:id/autofill_save_no")
	public MobileElement noThanksButton;
	
	@AndroidFindBy(id = "com.avito.android:id/btn_post_item")
	public MobileElement addPostItemInMenu;
	
	@AndroidFindBy(xpath = "//*[@text='Недвижимость']")
	public MobileElement propertyButton;
	
	@AndroidFindBy(xpath = "//*[@text='Квартиры']")
	public MobileElement apartmentButton;
	
	@AndroidFindBy(xpath = "//*[@text='Продам']")
	public MobileElement sellOffButton;
	
	@AndroidFindBy(xpath = "//*[@text='Вторичка']")
	public MobileElement resellersButton;
	
	@AndroidFindBy(id = "com.avito.android:id/title_view")
	public MobileElement createAdTitle;

	@AndroidFindBy(xpath = "//*[@text='Количество комнат']")
	public MobileElement roomsDropDownButton;
	
	@AndroidFindBys({@AndroidBy(className = "android.widget.TextView")})
	public List<MobileElement> roomsTextList;
	
	@AndroidFindBy(xpath = "//*[@text='Тип дома']")
	public MobileElement houseTypeDropDownButton;
	
	@AndroidFindBys({@AndroidBy(className = "android.widget.TextView")})
	public List<MobileElement> typesOfHouseTextList;
	
	@AndroidFindBy(xpath = "//*[@text='Этаж']")
	public MobileElement floorDropDownButton;
	
	@AndroidFindBys({@AndroidBy(className = "android.widget.TextView")})
	public List<MobileElement> floorNumberTextList;
	
	@AndroidFindBy(xpath = "//*[@text='Этажей в доме']")
	public MobileElement noOfFloorsDropDownButton;
	
	@AndroidFindBys({@AndroidBy(className = "android.widget.TextView")})
	public List<MobileElement> noOfFloorsTextList;
	
	@AndroidFindBy(xpath = "//*[@text='Право собственности']")
	public MobileElement ownershipDropDownButton;
	
	@AndroidFindBys({@AndroidBy(className = "android.widget.TextView")})
	public List<MobileElement> ownershipTextList;
	
	@AndroidFindBy(xpath = "//*[contains(@text, 'Общая площадь')]")
	public MobileElement totalArea;
	
	@AndroidFindBy(xpath = "//*[contains(@text, 'Площадь кухни')]")
	public MobileElement kitchenArea;
	
	@AndroidFindBy(xpath = "//*[contains(@text, 'Жилая площадь')]")
	public MobileElement livingArea;
	
	@AndroidFindBy(xpath = "//*[@text='Адрес']")
	public MobileElement addressLocation;
	
	@AndroidFindBy(id = "com.avito.android:id/edit_query")
	public MobileElement enterLocation;
	
	@AndroidFindBys({@AndroidBy(xpath = "//*[@resource-id='com.avito.android:id/drop_down_suggestions_container']//android.widget.LinearLayout")})
	public List<MobileElement> locationsDropDownList;
	
	@AndroidFindBy(id = "com.avito.android:id/main_button")
	public MobileElement submitLocation;
	
	@AndroidFindBy(xpath = "//*[contains(@text, 'Описание объявления')]")
	public MobileElement descriptionField;
	
	@AndroidFindBy(xpath = "//*[contains(@text, 'Цена')]")
	public MobileElement priceField;
	
	@AndroidFindBy(id = "com.avito.android:id/empty_view_content")
	public MobileElement addImagesButton;
	
	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
	public MobileElement allowPermissionsPopup;
	
	@AndroidFindBy(id = "com.avito.android:id/gallery_button")
	public MobileElement galleryButton;
	
	@AndroidFindBys({@AndroidBy(id = "android:id/title")})
	public List<MobileElement> galleryImagesList;
	
	@AndroidFindBy(id = "com.avito.android:id/main_button")
	public MobileElement continueButtonInImagesList;
	
	@AndroidFindBy(xpath = "//*[@text='Продолжить']")
	public MobileElement continueButton;
	
	@AndroidFindBy(id = "com.avito.android:id/fab_text")
	public MobileElement submitAdButton;
	
	@AndroidFindBy(xpath = "//*[@text='Без продвижения']")
	public MobileElement noPromotionAds;
	
	@AndroidFindBys({@AndroidBy(id = "com.avito.android:id/input")})
	public List<MobileElement> noEmailAtEnd;
	
	@AndroidFindBy(xpath = "(//*[@resource-id='com.avito.android:id/input_view'])[2]//android.widget.EditText")
	public MobileElement emailField;
	
	@AndroidFindBy(id = "advert_number")
	public MobileElement advatiseNumber;
	
}
