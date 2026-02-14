# 🎯 Quick Reference - New Locator Options

## All Locators Available Now

### When You Click an Element, You'll See:

```
1. cy.get('#id')                        - By ID attribute
2. cy.get('.classname')                 - By CSS class
3. cy.get('tag')                        - By HTML tag
4. cy.get('[type="value"]')             - By any attribute
5. cy.contains('text')                  - By visible text
6. cy.get('tag').contains('text')       - Tag that contains text
7. cy.get('tag').find('.class')         - Find class in tag
```

---

## Examples by Element Type

### Button
```html
<button id="btn-submit" class="primary" type="submit">
  Submit Form
</button>
```

Locators:
- `cy.get('#btn-submit')`
- `cy.get('.primary')`
- `cy.get('button')`
- `cy.get('[type="submit"]')`
- `cy.contains('Submit Form')`
- `cy.get('button').contains('Submit Form')`

### Input Field
```html
<input id="email" type="email" class="form-input" placeholder="Enter email">
```

Locators:
- `cy.get('#email')`
- `cy.get('[type="email"]')`
- `cy.get('.form-input')`
- `cy.get('input')`

### Link
```html
<a href="/login" class="nav-link">Click Here</a>
```

Locators:
- `cy.get('a')`
- `cy.get('.nav-link')`
- `cy.contains('Click Here')`
- `cy.get('a').contains('Click Here')`

---

## Text-Based Finding (cy.contains)

### Perfect For:
- Buttons with text labels
- Links with text
- Menu items
- Any user-visible text

### Examples:
```javascript
cy.contains('Login')              // Find any element with "Login"
cy.contains('Sign Up')            // Find "Sign Up" link
cy.contains('Submit')             // Find submit button
cy.contains('Click Here')         // Find that exact text
```

---

## Combined Selectors

### Tag + Text
```javascript
cy.get('button').contains('Login')     // Button with "Login" text
cy.get('a').contains('Sign Up')        // Link with "Sign Up" text
cy.get('input').contains('Search')     // Input with text
```

### Find in Parent
```javascript
cy.get('form').find('.button')         // Button inside form
cy.get('.modal').find('button')        // Button inside modal
cy.get('[role="dialog"]').find('a')    // Link inside dialog
```

---

## When to Use What

| Situation | Use This | Example |
|-----------|----------|---------|
| Element has ID | cy.get('#id') | cy.get('#submit') |
| Finding by text | cy.contains() | cy.contains('Login') |
| Element has class | cy.get('.class') | cy.get('.btn-primary') |
| Form input | cy.get('[type=...]') | cy.get('[type="email"]') |
| Any element | cy.get('tag') | cy.get('button') |
| Be more specific | cy.get().contains() | cy.get('button').contains('Save') |
| Element in parent | cy.find() | cy.get('form').find('.error') |

---

## 🎬 Workflow

1. Click element in browser
2. Dialog shows all 7 locator options
3. Pick the best one (usually ID first, then class, then text)
4. Click OK
5. Select action (Click, Type, etc.)
6. Code generated with your chosen locator

---

## ✨ Key Improvements

✅ 7 locator strategies instead of 3
✅ Text-based finding (cy.contains)
✅ Attribute selectors
✅ Combined selectors
✅ Find in parent support
✅ Auto-suggestions in dialog
✅ Better for dynamic/generated elements

---

**All locators ready to use! Select what works best for your element.** 🚀


