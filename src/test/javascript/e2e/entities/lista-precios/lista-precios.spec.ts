import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ListaPreciosComponentsPage, { ListaPreciosDeleteDialog } from './lista-precios.page-object';
import ListaPreciosUpdatePage from './lista-precios-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible
} from '../../util/utils';

const expect = chai.expect;

describe('ListaPrecios e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let listaPreciosComponentsPage: ListaPreciosComponentsPage;
  let listaPreciosUpdatePage: ListaPreciosUpdatePage;
  let listaPreciosDeleteDialog: ListaPreciosDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load ListaPrecios', async () => {
    await navBarPage.getEntityPage('lista-precios');
    listaPreciosComponentsPage = new ListaPreciosComponentsPage();
    expect(await listaPreciosComponentsPage.title.getText()).to.match(/Lista Precios/);

    expect(await listaPreciosComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([listaPreciosComponentsPage.noRecords, listaPreciosComponentsPage.table]);

    beforeRecordsCount = (await isVisible(listaPreciosComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(listaPreciosComponentsPage.table);
  });

  it('should load create ListaPrecios page', async () => {
    await listaPreciosComponentsPage.createButton.click();
    listaPreciosUpdatePage = new ListaPreciosUpdatePage();
    expect(await listaPreciosUpdatePage.getPageTitle().getAttribute('id')).to.match(/barsaAppApp.listaPrecios.home.createOrEditLabel/);
    await listaPreciosUpdatePage.cancel();
  });

  it('should create and save ListaPrecios', async () => {
    await listaPreciosComponentsPage.createButton.click();
    await listaPreciosUpdatePage.setDescripcionInput('descripcion');
    expect(await listaPreciosUpdatePage.getDescripcionInput()).to.match(/descripcion/);
    await listaPreciosUpdatePage.setPorcentajeInput('5');
    expect(await listaPreciosUpdatePage.getPorcentajeInput()).to.eq('5');
    await listaPreciosUpdatePage.setValorInput('5');
    expect(await listaPreciosUpdatePage.getValorInput()).to.eq('5');
    await waitUntilDisplayed(listaPreciosUpdatePage.saveButton);
    await listaPreciosUpdatePage.save();
    await waitUntilHidden(listaPreciosUpdatePage.saveButton);
    expect(await isVisible(listaPreciosUpdatePage.saveButton)).to.be.false;

    expect(await listaPreciosComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(listaPreciosComponentsPage.table);

    await waitUntilCount(listaPreciosComponentsPage.records, beforeRecordsCount + 1);
    expect(await listaPreciosComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last ListaPrecios', async () => {
    const deleteButton = listaPreciosComponentsPage.getDeleteButton(listaPreciosComponentsPage.records.last());
    await click(deleteButton);

    listaPreciosDeleteDialog = new ListaPreciosDeleteDialog();
    await waitUntilDisplayed(listaPreciosDeleteDialog.deleteModal);
    expect(await listaPreciosDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/barsaAppApp.listaPrecios.delete.question/);
    await listaPreciosDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(listaPreciosDeleteDialog.deleteModal);

    expect(await isVisible(listaPreciosDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([listaPreciosComponentsPage.noRecords, listaPreciosComponentsPage.table]);

    const afterCount = (await isVisible(listaPreciosComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(listaPreciosComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
