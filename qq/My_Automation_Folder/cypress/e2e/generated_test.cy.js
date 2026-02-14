import { PageObjects } from '../pages/PageObjects';

describe('Automation Suite', () => {
  const page = new PageObjects();
  it('Generated User Flow', () => {
    cy.visit('https://dev.zeustra.com');
    page.cc.should('be.visible').click();
  });
});