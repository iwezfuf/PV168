package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.service.validation.Validator;
import net.miginfocom.swing.MigLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Objects;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();
    private final Validator<E> entityValidator;

    EntityDialog(Validator<E> entityValidator) {
        this.entityValidator = Objects.requireNonNull(entityValidator);
        panel.setLayout(new MigLayout("wrap 2"));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        panel.add(label);
        panel.add(component, "wmin 250lp, grow");
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);

        // TODO implement validatior
        if (result == OK_OPTION) {
            return Optional.of(getEntity());
        } else {
            return Optional.empty();
        }
    }
}
