package worms.model.program;

import java.util.List;
import worms.model.Entity;
import worms.model.program.exceptions.IllegalTypeException;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;
import worms.model.world.entity.Food;
import worms.model.world.entity.Worm;
import worms.util.Util;

// TODO : Is line/column meant to just be used to give more accurate exceptions? As in give the user where the exception happend in his program. 
// TODO : Still something fishy about the exceptions? compile-time? -> Don't stop but just store the exception error thrown so we can see all exceptions?

/**
 * The class implementing the ProgramFactory which handles our design.
 * 
 * When a wrong type of expression or .. is presented an exception will be thrown.
 * This is not stated in the function itself since we don't allow anyone to catch them as we want the program to terminate.
 *
 * @author Derkinderen Vincent
 * @author Coosemans Brent
 */
public class ProgramFactoryImpl implements ProgramFactory<Expression, Statement, Variable> {
    
    //TODO: Finish up + doc + null pointer
    public void setProgramParser(ProgramParser parser) {
        this.parser = parser;
    }

    ProgramParser parser;
    
    @Override
    public DoubleExpression createDoubleLiteral(int line, int column, final double d) {
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return d;
            }
        };
    }

    @Override
    public BooleanExpression createBooleanLiteral(int line, int column, final boolean b) {
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return b;
            }
        };
    }

    @Override
    public BooleanExpression createAnd(int line, int column, final Expression e1, final Expression e2) {
        if (!(e1 instanceof BooleanExpression) || !(e2 instanceof BooleanExpression))
            throw new IllegalTypeException("An &&-expression must consist of atleast 2 BooleanExpressions");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((BooleanExpression) e1).getResult() && ((BooleanExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createOr(int line, int column, final Expression e1, final Expression e2) {
        if (!(e1 instanceof BooleanExpression) || !(e2 instanceof BooleanExpression))
            throw new IllegalTypeException("An ||-expression must consist of atleast 2 BooleanExpressions");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((BooleanExpression) e1).getResult() && ((BooleanExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createNot(int line, int column, final Expression e) {
        if (!(e instanceof BooleanExpression))
            throw new IllegalTypeException("The !-expression must consist of a BooleanExpression.");
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return !((BooleanExpression) e).getResult();
            }
        };
    }

    @Override
    public EntityExpression createNull(int line, int column) {
        return new EntityExpression() {
            @Override
            public Entity getResult() {
                return null;
            }
        };
    }

    @Override
    public EntityExpression createSelf(int line, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DoubleExpression createGetX(int line, int column, final Expression e) {
        if(!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                //can throw NullPointerException when necessary :)
                return ((EntityExpression) e).getResult().getPosition().getX();
            }
        };
    }

    @Override
    public DoubleExpression createGetY(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getPosition().getY();
            }
        };
    }

    @Override
    public DoubleExpression createGetRadius(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getRadius();
            }
        };
    }

    @Override
    public DoubleExpression createGetDir(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getAngle();
            }
        };
    }

    @Override
    public DoubleExpression createGetAP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getAP();
            }
        };
    }

    @Override
    public DoubleExpression createGetMaxAP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getMaxAP();
            }
        };
    }

    @Override
    public DoubleExpression createGetHP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getHP();
            }
        };
    }

    @Override
    public DoubleExpression createGetMaxHP(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return ((EntityExpression) e).getResult().getMaxHP();
            }
        };
    }

    @Override
    public BooleanExpression createSameTeam(int line, int column, Expression e) {
        //TODO suggestion so far: add getTeam to Entity??
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntityExpression createSearchObj(int line, int column, Expression e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BooleanExpression createIsWorm(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((EntityExpression) e).getResult() instanceof Worm;
            }
        };
    }

    @Override
    public BooleanExpression createIsFood(int line, int column, final Expression e) {
        if (!(e instanceof EntityExpression))
            throw new IllegalTypeException("The parameter must be an entity.");

        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((EntityExpression) e).getResult() instanceof Food;
            }
        };
    }

    @Override
    public Expression createVariableAccess(int line, int column, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BooleanExpression createLessThan(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((DoubleExpression) e1).getResult()<((DoubleExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createGreaterThan(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return ((DoubleExpression) e1).getResult()>((DoubleExpression) e2).getResult();
            }
        };
    }

    @Override
    public BooleanExpression createLessThanOrEqualTo(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Util.fuzzyLessThanOrEqualTo(((DoubleExpression) e1).getResult(),((DoubleExpression) e2).getResult()));
            }
        };
    }

    @Override
    public BooleanExpression createGreaterThanOrEqualTo(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (Util.fuzzyGreaterThanOrEqualTo(((DoubleExpression) e1).getResult(),((DoubleExpression) e2).getResult()));
            }
        };
    }

    @Override
    public BooleanExpression createEquality(int line, int column, final Expression e1, final Expression e2) {
        //If different expression types it will return false anyway, so we don't check it. no worries.
        //TODO: (vraag) moeten wij zelf een error geven?
        return new BooleanExpression() {
            @Override
            public Boolean getResult() {
                return (e1.getResult()==e2.getResult());
            }
        };
    }

    @Override
    public BooleanExpression createInequality(int line, int column, Expression e1, Expression e2) {
        return this.createNot(line, column, this.createEquality(line, column, e1, e2));
    }

    @Override
    public DoubleExpression createAdd(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()+((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSubtraction(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()-((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createMul(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()*((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createDivision(int line, int column, final Expression e1, final Expression e2) {
        if(!(e1 instanceof DoubleExpression) || !(e2 instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return (((DoubleExpression) e1).getResult()/((DoubleExpression) e2).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSqrt(int line, int column, final Expression e) {
        if(!(e instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.sqrt(((DoubleExpression) e).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createSin(int line, int column, final Expression e) {
        if(!(e instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.sin(((DoubleExpression) e).getResult());
            }
        };
    }

    @Override
    public DoubleExpression createCos(int line, int column, final Expression e) {
        if(!(e instanceof DoubleExpression))
            throw new IllegalTypeException();
        
        return new DoubleExpression() {
            @Override
            public Double getResult() {
                return Math.cos(((DoubleExpression) e).getResult());
            }
        };
    }

    @Override
    public Statement createTurn(int line, int column, Expression angle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createMove(int line, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createJump(int line, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createToggleWeap(int line, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createFire(int line, int column, Expression yield) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createSkip(int line, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createAssignment(int line, int column, String variableName, Expression rhs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createIf(int line, int column, Expression condition, Statement then, Statement otherwise) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createWhile(int line, int column, Expression condition, Statement body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createForeach(int line, int column, ForeachType type, String variableName, Statement body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createSequence(int line, int column, List<Statement> statements) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement createPrint(int line, int column, Expression e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Variable createDoubleType() {
        return new Variable(Double.class);
    }

    @Override
    public Variable createBooleanType() {
        return new Variable(Boolean.class);
    }

    @Override
    public Variable createEntityType() {
        return new Variable(Entity.class);
    }

}
