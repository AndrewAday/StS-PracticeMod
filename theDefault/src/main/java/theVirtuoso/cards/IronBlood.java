package theVirtuoso.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuoso.DefaultMod;

import static theVirtuoso.DefaultMod.makeCardPath;

public class IronBlood extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IronBlood.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.RED;

    private static final int COST = 2;

    private static final int REMOVE_BLOCK = 10;
    private static final int UPGRADE_PLUS_REMOVE_BLOCK = -3;

    private static final int HEAL_AMOUNT = 5;
    private static final int UPGRADE_PLUS_HEAL_AMOUNT = 2;


    public IronBlood() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = REMOVE_BLOCK;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = HEAL_AMOUNT;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_REMOVE_BLOCK);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_HEAL_AMOUNT);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new LoseBlockAction(p, p, this.magicNumber)
        );
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(p, p, this.defaultSecondMagicNumber)
        );
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            return p.currentBlock >= this.magicNumber;
        }
    }
}
