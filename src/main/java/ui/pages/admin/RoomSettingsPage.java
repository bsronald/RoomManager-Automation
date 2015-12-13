package ui.pages.admin;

import entities.ConferenceRoom;
import entities.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ui.BasePageObject;

/**
 * Created by jorgeavila on 12/7/2015.
 */
public class RoomSettingsPage extends BasePageObject {

    @FindBy(xpath = "//form/div[2]/input")
    WebElement displayNameInput;

    @FindBy(xpath = "//form/div[3]/input")
    WebElement codeInput;

    @FindBy(xpath = "//form/div[4]/input")
    WebElement capacityInput;

    @FindBy(xpath = "//button[.//span[contains(.,'Save')]]")
    WebElement saveButton;

    @FindBy(xpath = "//label[contains(text(),'Location')]/..//div[@id='add-location']")
    WebElement locationComboBox;

    @FindBy(xpath = "//a[contains(text(),'Resource Associations')]")
    WebElement resourceAssociateTab;

    WebElement powerOffRoomButton;

    public RoomSettingsPage(){
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    public RoomSettingsPage typeDisplayNameInput(String displayName){
        displayNameInput.clear();
        displayNameInput.sendKeys(displayName);
        return this;
    }

    public RoomSettingsPage typeCodeInput(String code){
        codeInput.clear();
        codeInput.sendKeys(code);
        return this;
    }

    public RoomSettingsPage typeCapacityInput(String capacity){
        capacityInput.clear();
        capacityInput.sendKeys(capacity);
        return this;
    }

    public ConferenceRoomsPage clickOnSaveButton(){
        saveButton.click();
        return new ConferenceRoomsPage();
    }

    public ConferenceRoomsPage fillForm(ConferenceRoom confRoom){
        typeDisplayNameInput(confRoom.getCustomDisplayName());
        typeCodeInput(confRoom.getCode());
        typeCapacityInput(confRoom.getCapacity());
        return clickOnSaveButton();
    }

    @Override
    public void waitUntilPageObjectIsLoaded() {
        driverWait.until(ExpectedConditions.visibilityOf(saveButton));
    }

    public boolean isLocationPresent(Location location) {
        return locationComboBox.getText().contains(location.getName());
    }

    public ResourceAssociatePage clickOnResourceAssociateTab() {
        resourceAssociateTab.click();
        return new ResourceAssociatePage();
    }

    public ConferenceRoomsPage clickOnPowerOffRoomButton(ConferenceRoom conferenceRoom) {
        By powerOffLocator = By.xpath("//h2[contains(.,'" + conferenceRoom.getCustomDisplayName() +"')]/../following::div/button");
        powerOffRoomButton = driver.findElement(powerOffLocator);
        powerOffRoomButton.click();
        clickOnSaveButton();
        By confirmationMessageLocator =By.xpath("//div[contains(text(),'Room " +conferenceRoom.getCustomDisplayName()+ " was disabled')]");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessageLocator));
        return new ConferenceRoomsPage();
    }
}
