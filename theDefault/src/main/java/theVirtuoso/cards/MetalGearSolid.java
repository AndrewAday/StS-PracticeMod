package theVirtuoso.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theVirtuoso.DefaultMod;

import static theVirtuoso.DefaultMod.makeCardPath;

public class MetalGearSolid extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(MetalGearSolid.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.RED;

    private static final int COST = 2;

    private static final int SELF_DAMAGE = 10;
    private static final int UPGRADE_PLUS_SELF_DAMAGE = -3;

    private static final int PLATED_ARMOR = 7;
    private static final int UPGRADE_PLUS_PLATED_ARMOR = 3;


    public MetalGearSolid() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = SELF_DAMAGE;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = PLATED_ARMOR;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_SELF_DAMAGE);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_PLATED_ARMOR);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new LoseHPAction(p, p, this.magicNumber)
        );
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(
                p, p, new PlatedArmorPower(p, defaultSecondMagicNumber), defaultSecondMagicNumber
            )
        );
    }
}
