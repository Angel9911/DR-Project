import { AppPage } from './app.po';
import { browser, logging } from 'protractor';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getTitleText()).equal('Welcome to package!');
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser().log().get(logging.Type.BROWSER);
    expect(logs).not.contain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    }));
  });
});
