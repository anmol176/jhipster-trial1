import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CompanyEntityAccountUpdatePage from './company-entity-account-update.page-object';

const expect = chai.expect;
export class CompanyEntityAccountDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('trial1App.companyEntityAccount.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-companyEntityAccount'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CompanyEntityAccountComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('company-entity-account-heading'));
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
    await navBarPage.getEntityPage('company-entity-account');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCompanyEntityAccount() {
    await this.createButton.click();
    return new CompanyEntityAccountUpdatePage();
  }

  async deleteCompanyEntityAccount() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const companyEntityAccountDeleteDialog = new CompanyEntityAccountDeleteDialog();
    await waitUntilDisplayed(companyEntityAccountDeleteDialog.deleteModal);
    expect(await companyEntityAccountDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /trial1App.companyEntityAccount.delete.question/
    );
    await companyEntityAccountDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(companyEntityAccountDeleteDialog.deleteModal);

    expect(await isVisible(companyEntityAccountDeleteDialog.deleteModal)).to.be.false;
  }
}
