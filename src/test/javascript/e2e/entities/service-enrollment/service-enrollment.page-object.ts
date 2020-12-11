import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServiceEnrollmentUpdatePage from './service-enrollment-update.page-object';

const expect = chai.expect;
export class ServiceEnrollmentDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('trial1App.serviceEnrollment.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-serviceEnrollment'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServiceEnrollmentComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('service-enrollment-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('service-enrollment');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateServiceEnrollment() {
    await this.createButton.click();
    return new ServiceEnrollmentUpdatePage();
  }

  async deleteServiceEnrollment() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const serviceEnrollmentDeleteDialog = new ServiceEnrollmentDeleteDialog();
    await waitUntilDisplayed(serviceEnrollmentDeleteDialog.deleteModal);
    expect(await serviceEnrollmentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/trial1App.serviceEnrollment.delete.question/);
    await serviceEnrollmentDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(serviceEnrollmentDeleteDialog.deleteModal);

    expect(await isVisible(serviceEnrollmentDeleteDialog.deleteModal)).to.be.false;
  }
}
