# 📖 Quick Reference - New Features

## 1️⃣ Persistent File Browser

**What:** Browser remembers last opened directory

**Where:** `~/.ghosttester/prefs.txt`

**How to Use:**
1. Click "Browse Folder"
2. Navigate to your project folder
3. Select it → **Automatically saved!**
4. Next time you click Browse → Opens at same location

**Implementation:**
- `PreferenceManager.java` - Handles persistence
- Auto-loads on startup
- Auto-saves on directory selection

---

## 2️⃣ Remove Element from DOM

**What:** Clear obstacles by removing elements from page

**Action:** `[REMOVE]` Remove from DOM

**Generated Code:**
```javascript
page.element.invoke('remove'); // Element removed from DOM
```

**Use Cases:**
- ✓ Remove blocking modals
- ✓ Clear overlays
- ✓ Test accessibility
- ✓ Verify fallback UI

**Example:**
```javascript
page.modalOverlay.invoke('remove'); // Clear the way
page.submitButton.click(); // Now accessible!
```

---

## 3️⃣ Tooltip Assertion

**What:** Verify element has specific tooltip text

**Action:** `[TOOLTIP]` Check Tooltip

**Prompts for:** Expected tooltip text

**Generated Code:**
```javascript
page.element.should('have.attr', 'title', 'tooltip text');
```

**Use Cases:**
- ✓ Accessibility testing
- ✓ UX content verification
- ✓ Help text validation

**Example:**
```javascript
page.helpIcon.should('have.attr', 'title', 'Click for help');
```

---

## 🔄 All Actions Now Available

| # | Action | Icon | Use |
|---|--------|------|-----|
| 1 | Click | `[CLICK]` | Mouse click |
| 2 | Type | `[TYPE]` | Enter text |
| 3 | Hover | `[HOVER]` | Mouse hover |
| 4 | ScrollIntoView | `[SCROLL]` | Scroll to view |
| 5 | Assert Visible | `[ASSERT]` | Check visibility |
| 6 | Check Tooltip | `[TOOLTIP]` | ⭐ NEW |
| 7 | Remove from DOM | `[REMOVE]` | ⭐ NEW |
| 8 | Wait | `[WAIT]` | Just wait |
| 9 | Wait & Click | `[WAIT+CLICK]` | Combined |

---

## 📝 Generated Code Examples

### Remove Overlay + Click Button
```javascript
// Input:
// - Click element: modal overlay
// - Select "Remove from DOM"
// - Then click button

// Output:
page.modalOverlay.invoke('remove'); // Element removed from DOM
page.actionButton.click();
```

### Verify Tooltip on Icon
```javascript
// Input:
// - Click element: info icon
// - Select "Check Tooltip"
// - Enter text: "More information"

// Output:
page.infoIcon.should('have.attr', 'title', 'More information');
```

### Complex Workflow
```javascript
// Combined actions in one test
page.overlay.invoke('remove'); // Remove blocker
page.form.wait(2000); // Wait
page.form.should('be.visible'); // Verify
page.submitBtn.should('have.attr', 'title', 'Submit form'); // Tooltip
page.submitBtn.click(); // Action
```

---

## 🎯 Workflow

### Old Way (Before)
```
1. Browse for project
2. Close app
3. Reopen app
4. Browse again - starts at home directory ❌
5. Navigate back to project folder (wasted time!)
```

### New Way (After)
```
1. Browse for project
2. Close app
3. Reopen app
4. Browse again - opens at last location ✅
5. Select folder immediately!
```

---

## ✨ Key Improvements

| Feature | Benefit |
|---------|---------|
| **Persistent Directory** | No re-navigation needed |
| **Remove from DOM** | Handle blocking elements |
| **Tooltip Assertion** | Better test coverage |

---

## 🚀 Ready to Use!

All features are:
- ✅ Compiled successfully
- ✅ Integrated seamlessly
- ✅ Ready for production
- ✅ Fully documented

**Status:** READY TO USE 🎉


