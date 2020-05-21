import { browser, element, by, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PedidoCabeceraComponentsPage, { PedidoCabeceraDeleteDialog } from './pedido-cabecera.page-object';
import PedidoCabeceraUpdatePage from './pedido-cabecera-update.page-object';
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

describe('PedidoCabecera e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pedidoCabeceraComponentsPage: PedidoCabeceraComponentsPage;
  let pedidoCabeceraUpdatePage: PedidoCabeceraUpdatePage;
  let pedidoCabeceraDeleteDialog: PedidoCabeceraDeleteDialog;
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

  it('should load PedidoCabeceras', async () => {
    await navBarPage.getEntityPage('pedido-cabecera');
    pedidoCabeceraComponentsPage = new PedidoCabeceraComponentsPage();
    expect(await pedidoCabeceraComponentsPage.title.getText()).to.match(/Pedido Cabeceras/);

    expect(await pedidoCabeceraComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([pedidoCabeceraComponentsPage.noRecords, pedidoCabeceraComponentsPage.table]);

    beforeRecordsCount = (await isVisible(pedidoCabeceraComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(pedidoCabeceraComponentsPage.table);
  });

  it('should load create PedidoCabecera page', async () => {
    await pedidoCabeceraComponentsPage.createButton.click();
    pedidoCabeceraUpdatePage = new PedidoCabeceraUpdatePage();
    expect(await pedidoCabeceraUpdatePage.getPageTitle().getAttribute('id')).to.match(/barsaAppApp.pedidoCabecera.home.createOrEditLabel/);
    await pedidoCabeceraUpdatePage.cancel();
  });

  it('should create and save PedidoCabeceras', async () => {
    await pedidoCabeceraComponentsPage.createButton.click();
    await pedidoCabeceraUpdatePage.setFechaCreacionInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await pedidoCabeceraUpdatePage.getFechaCreacionInput()).to.contain('2001-01-01T02:30');
    await pedidoCabeceraUpdatePage.setFechaEntregaInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await pedidoCabeceraUpdatePage.getFechaEntregaInput()).to.contain('2001-01-01T02:30');
    await pedidoCabeceraUpdatePage.setPedidoNumeroInput('5');
    expect(await pedidoCabeceraUpdatePage.getPedidoNumeroInput()).to.eq('5');
    await pedidoCabeceraUpdatePage.setDescripcionInput('descripcion');
    expect(await pedidoCabeceraUpdatePage.getDescripcionInput()).to.match(/descripcion/);
    const selectedFacturado = await pedidoCabeceraUpdatePage.getFacturadoInput().isSelected();
    if (selectedFacturado) {
      await pedidoCabeceraUpdatePage.getFacturadoInput().click();
      expect(await pedidoCabeceraUpdatePage.getFacturadoInput().isSelected()).to.be.false;
    } else {
      await pedidoCabeceraUpdatePage.getFacturadoInput().click();
      expect(await pedidoCabeceraUpdatePage.getFacturadoInput().isSelected()).to.be.true;
    }
    const selectedEntregado = await pedidoCabeceraUpdatePage.getEntregadoInput().isSelected();
    if (selectedEntregado) {
      await pedidoCabeceraUpdatePage.getEntregadoInput().click();
      expect(await pedidoCabeceraUpdatePage.getEntregadoInput().isSelected()).to.be.false;
    } else {
      await pedidoCabeceraUpdatePage.getEntregadoInput().click();
      expect(await pedidoCabeceraUpdatePage.getEntregadoInput().isSelected()).to.be.true;
    }
    await pedidoCabeceraUpdatePage.setTotalBrutoInput('5');
    expect(await pedidoCabeceraUpdatePage.getTotalBrutoInput()).to.eq('5');
    await pedidoCabeceraUpdatePage.setTotalIvaInput('5');
    expect(await pedidoCabeceraUpdatePage.getTotalIvaInput()).to.eq('5');
    await pedidoCabeceraUpdatePage.setTotalImpConsumoInput('5');
    expect(await pedidoCabeceraUpdatePage.getTotalImpConsumoInput()).to.eq('5');
    await pedidoCabeceraUpdatePage.setTotalInput('5');
    expect(await pedidoCabeceraUpdatePage.getTotalInput()).to.eq('5');
    await pedidoCabeceraUpdatePage.clienteNoSelectLastOption();
    await waitUntilDisplayed(pedidoCabeceraUpdatePage.saveButton);
    await pedidoCabeceraUpdatePage.save();
    await waitUntilHidden(pedidoCabeceraUpdatePage.saveButton);
    expect(await isVisible(pedidoCabeceraUpdatePage.saveButton)).to.be.false;

    expect(await pedidoCabeceraComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(pedidoCabeceraComponentsPage.table);

    await waitUntilCount(pedidoCabeceraComponentsPage.records, beforeRecordsCount + 1);
    expect(await pedidoCabeceraComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last PedidoCabecera', async () => {
    const deleteButton = pedidoCabeceraComponentsPage.getDeleteButton(pedidoCabeceraComponentsPage.records.last());
    await click(deleteButton);

    pedidoCabeceraDeleteDialog = new PedidoCabeceraDeleteDialog();
    await waitUntilDisplayed(pedidoCabeceraDeleteDialog.deleteModal);
    expect(await pedidoCabeceraDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/barsaAppApp.pedidoCabecera.delete.question/);
    await pedidoCabeceraDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(pedidoCabeceraDeleteDialog.deleteModal);

    expect(await isVisible(pedidoCabeceraDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([pedidoCabeceraComponentsPage.noRecords, pedidoCabeceraComponentsPage.table]);

    const afterCount = (await isVisible(pedidoCabeceraComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(pedidoCabeceraComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
