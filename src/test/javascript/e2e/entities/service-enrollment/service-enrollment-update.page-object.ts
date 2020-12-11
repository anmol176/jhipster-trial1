import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class ServiceEnrollmentUpdatePage {
  pageTitle: ElementFinder = element(by.id('trial1App.serviceEnrollment.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  serviceNameSelect: ElementFinder = element(by.css('select#service-enrollment-serviceName'));
  uenInput: ElementFinder = element(by.css('input#service-enrollment-uen'));
  uploadServiceRequestDocumentInput: ElementFinder = element(by.css('input#file_uploadServiceRequestDocument'));
  serviceDescriptionInput: ElementFinder = element(by.css('input#service-enrollment-serviceDescription'));
  statusSelect: ElementFinder = element(by.css('select#service-enrollment-status'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setServiceNameSelect(serviceName) {
    await this.serviceNameSelect.sendKeys(serviceName);
  }

  async getServiceNameSelect() {
    return this.serviceNameSelect.element(by.css('option:checked')).getText();
  }

  async serviceNameSelectLastOption() {
    await this.serviceNameSelect.all(by.tagName('option')).last().click();
  }
  async setUenInput(uen) {
    await this.uenInput.sendKeys(uen);
  }

  async getUenInput() {
    return this.uenInput.getAttribute('value');
  }

  async setUploadServiceRequestDocumentInput(uploadServiceRequestDocument) {
    await this.uploadServiceRequestDocumentInput.sendKeys(uploadServiceRequestDocument);
  }

  async getUploadServiceRequestDocumentInput() {
    return this.uploadServiceRequestDocumentInput.getAttribute('value');
  }

  async setServiceDescriptionInput(serviceDescription) {
    await this.serviceDescriptionInput.sendKeys(serviceDescription);
  }

  async getServiceDescriptionInput() {
    return this.serviceDescriptionInput.getAttribute('value');
  }

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }
  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.serviceNameSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setUenInput('uen');
    expect(await this.getUenInput()).to.match(/uen/);
    await waitUntilDisplayed(this.saveButton);
    await this.setUploadServiceRequestDocumentInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setServiceDescriptionInput('serviceDescription');
    expect(await this.getServiceDescriptionInput()).to.match(/serviceDescription/);
    await waitUntilDisplayed(this.saveButton);
    await this.statusSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
