import { PageObjects } from '../pages/PageObjects';

describe('Automation Flow', () => {
  const page = new PageObjects();
  it('Steps', () => {
    cy.visit('https://dev.zeustra.com');
  });
    cy.wait(2000);
    page.xc.click();
  });
});